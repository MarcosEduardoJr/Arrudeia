package com.arrudeia.core.sign.domain

import com.arrudeia.core.sign.data.SignFirebaseUserRepositoryImpl
import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity
import com.arrudeia.core.sign.domain.entity.SignFirebaseUserUseCaseEntity
import javax.inject.Inject

class SignInUserFirebaseUseCase @Inject constructor(
    private val repository: SignFirebaseUserRepositoryImpl,
) {
    suspend operator fun invoke(email: String, password: String): SignFirebaseUserUseCaseEntity? =
        repository.signUserWithEmailAndPassword(email, password).mapToTravelUseCaseEntity()

    private fun SignFirebaseUserRepositoryEntity?.mapToTravelUseCaseEntity(): SignFirebaseUserUseCaseEntity? {
        return if (this == null) null
        else
            return SignFirebaseUserUseCaseEntity(
                this.uid,
                this.name,
                this.email
            )
    }

}
