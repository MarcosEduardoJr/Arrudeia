package com.arrudeia.feature.services.domain

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.ServiceRepositoryImpl
import com.arrudeia.feature.services.domain.entity.ServiceUserUseCaseEntity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CreateServiceUseCase @Inject constructor(
    private val repository: ServiceRepositoryImpl,
    private val firebaseAuth: FirebaseAuth,
) {
    suspend operator fun invoke(service : ServiceUserUseCaseEntity): Result<Int?> {
        service.uuid_user_creator = firebaseAuth.uid.orEmpty()
       return repository.createService(service.toServiceUserRepositoryEntity())
    }
}
