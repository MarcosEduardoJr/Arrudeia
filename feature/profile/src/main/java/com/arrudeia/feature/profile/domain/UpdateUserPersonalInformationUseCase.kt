package com.arrudeia.feature.profile.domain

import android.net.Uri
import com.arrudeia.core.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.data.network.FirebaseUserRepositoryImpl
import com.arrudeia.core.data.network.ProfileRepositoryImpl
import com.arrudeia.core.entity.UserPersonalInformationUseCaseEntity
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.profile.data.entity.ProfileDataStoreUserRepositoryEntity
import javax.inject.Inject

class UpdateUserPersonalInformationUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
    private val firebaseUserRepositoryImpl: FirebaseUserRepositoryImpl
) {
    suspend operator fun invoke(user: UserPersonalInformationUseCaseEntity, image: Uri?): String? {
        image?.let { img ->
            firebaseUserRepositoryImpl.saveUserImage(img)?.let {
                user.profileImage = it
            }
        }
        val result = repository.updateUserPersonalInformation(user.toRepositoryEntity())
        repositoryDataStore.saveUser(user.toDataStoreRepositoryEntity())
        return result
    }

    private fun UserPersonalInformationUseCaseEntity.toRepositoryEntity(): UserPersonalInformationRepositoryEntity {
        return UserPersonalInformationRepositoryEntity(
            uuid = this.uuid,
            name = this.name,
            email = this.email,
            phone = this.phone,
            idDocument = this.idDocument,
            birthDate = this.birthDate,
            profileImage = this.profileImage
        )
    }

    private fun UserPersonalInformationUseCaseEntity.toDataStoreRepositoryEntity(): ProfileDataStoreUserRepositoryEntity {
        return  ProfileDataStoreUserRepositoryEntity(
            uid = this.uuid.orEmpty(),
            name = this.name.orEmpty(),
            email = this.email.orEmpty(),
            image = this.profileImage.orEmpty(),
        )
    }

}
