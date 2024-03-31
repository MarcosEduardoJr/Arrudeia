package com.arrudeia.core.domain

import com.arrudeia.core.data.network.FirebaseUserRepositoryImpl
import com.arrudeia.core.data.entity.FirebaseUserRepositoryEntity
import com.arrudeia.core.entity.FirebaseUserUseCaseEntity
import javax.inject.Inject

class CreateUserFirebaseUseCase @Inject constructor(
    private val repository: FirebaseUserRepositoryImpl,
) {
    suspend operator fun invoke(email: String, password: String): FirebaseUserUseCaseEntity? =
        repository.createUserWithEmailAndPassword(email, password).mapToTravelUseCaseEntity()

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
