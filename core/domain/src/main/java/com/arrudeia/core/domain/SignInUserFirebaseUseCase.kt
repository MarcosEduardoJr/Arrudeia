package com.arrudeia.core.domain

import com.arrudeia.core.data.network.FirebaseUserRepositoryImpl
import com.arrudeia.core.data.repository.FirebaseUserRepositoryEntity
import com.arrudeia.core.entity.FirebaseUserUseCaseEntity
import javax.inject.Inject

class SignInUserFirebaseUseCase @Inject constructor(
    private val repository: FirebaseUserRepositoryImpl,
) {
    suspend operator fun invoke(email: String, password: String): FirebaseUserUseCaseEntity? =
        repository.signUserWithEmailAndPassword(email, password).mapToTravelUseCaseEntity()

    private fun FirebaseUserRepositoryEntity?.mapToTravelUseCaseEntity(): FirebaseUserUseCaseEntity? {
        return if (this == null) null
        else
            return FirebaseUserUseCaseEntity(
                this.uid,
                this.name,
                this.email
            )
    }

}
