package com.arrudeia.feature.home.domain

import com.arrudeia.feature.home.data.HomeProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.home.data.HomeProfileRepositoryImpl
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.feature.home.domain.entity.UserPersonalInformationUseCaseEntity
import javax.inject.Inject

class GetUserPersonalInformationUseCase @Inject constructor(
    private val repository: HomeProfileRepositoryImpl,
    private val repositoryDataStore: HomeProfileDataStoreUserRepositoryImpl,
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
