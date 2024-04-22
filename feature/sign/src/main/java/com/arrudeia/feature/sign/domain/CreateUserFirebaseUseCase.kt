package com.arrudeia.feature.sign.domain

import com.arrudeia.feature.sign.data.SignFirebaseUserRepositoryImpl
import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity
import com.arrudeia.feature.sign.domain.entity.SignFirebaseUserUseCaseEntity
import javax.inject.Inject

class CreateUserFirebaseUseCase @Inject constructor(
    private val repository: SignFirebaseUserRepositoryImpl,
) {
    suspend operator fun invoke(email: String, password: String): SignFirebaseUserUseCaseEntity? =
        repository.createUserWithEmailAndPassword(email, password).mapToUseCaseEntity()

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
