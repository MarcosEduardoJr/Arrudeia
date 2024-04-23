package com.arrudeia.feature.profile.domain

import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.ProfileRepositoryImpl
import com.arrudeia.feature.profile.domain.entity.UserPersonalInformationUseCaseEntity
import javax.inject.Inject

class GetUserPersonalInformationUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(): UserPersonalInformationUseCaseEntity? =
        repository.getUserPersonalInformationDetails(repositoryDataStore.getUserData()?.uid.orEmpty())
            .toUseCaseEntity()

    private fun UserPersonalInformationRepositoryEntity?.toUseCaseEntity(): UserPersonalInformationUseCaseEntity? {
        var result: UserPersonalInformationUseCaseEntity? = null
        this?.let {
            result = UserPersonalInformationUseCaseEntity(
                uuid = it.uuid,
                name = it.name,
                email = it.email,
                phone = it.phone,
                idDocument = it.idDocument,
                birthDate = it.birthDate,
                profileImage = it.profileImage
            )
        }
        return result
    }

}