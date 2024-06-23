package com.arrudeia.feature.services.domain

import android.net.Uri
import com.arrudeia.feature.services.data.FirebaseArrudeiaMapRepositoryImpl
import com.arrudeia.feature.services.data.ServiceRepositoryImpl
import com.arrudeia.feature.services.domain.entity.ServiceImageUseCaseEntity
import com.arrudeia.feature.services.domain.entity.ServiceUserImageUseCase
import com.arrudeia.feature.services.domain.entity.ServiceUserUseCaseEntity
import com.arrudeia.feature.services.presentation.model.NewServiceUserImageUiModel
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID
import javax.inject.Inject

class CreateServiceImageUseCase @Inject constructor(
    private val repository: FirebaseArrudeiaMapRepositoryImpl,
) {
    suspend operator fun invoke(listUri: List<Uri>): List<ServiceUserImageUseCase> {
        val list = mutableListOf<ServiceUserImageUseCase>()
        listUri.forEach {
            list.add(
                ServiceUserImageUseCase(
                    0,
                    0,
                    repository.savePlaceImage(UUID.randomUUID().toString(), it).orEmpty()
                )
            )
        }
        return list
    }
}