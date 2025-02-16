package com.arrudeia.core.sign.domain

import com.arrudeia.core.sign.data.CreateUserRepositoryImpl
import com.arrudeia.core.sign.data.SignDataStoreUserRepository
import com.arrudeia.core.sign.data.SignFirebaseUserRepositoryImpl
import com.arrudeia.core.sign.domain.entity.SignFirebaseUserUseCaseEntity
import com.arrudeia.feature.sign.data.entity.SignDataStoreUserRepositoryEntity
import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity
import javax.inject.Inject

class CreateUserFirebaseUseCase @Inject constructor(
    private val repository: SignFirebaseUserRepositoryImpl,
    private val createUserRepository: CreateUserRepositoryImpl,
    private val repositoryDataStore: SignDataStoreUserRepository,
) {
    suspend operator fun invoke(email: String, password: String): SignFirebaseUserUseCaseEntity? {
        val resultFirebase =
            repository.createUserWithEmailAndPassword(email, password).mapToUseCaseEntity()
        val result = createUserRepository.createUser(
            uuid = resultFirebase!!.uid,
            name = resultFirebase.name,
            email = resultFirebase.email,
        )
        repositoryDataStore.saveUser(
            SignDataStoreUserRepositoryEntity(
                resultFirebase.uid,
                resultFirebase.name,
                resultFirebase.email,
                isSavedDoc = false,
                image = ""
            )
        )
        return resultFirebase
    }

    private fun SignFirebaseUserRepositoryEntity?.mapToUseCaseEntity(): SignFirebaseUserUseCaseEntity? {
        return if (this == null) null
        else
            return SignFirebaseUserUseCaseEntity(
                this.uid,
                this.name,
                this.email
            )
    }

}
