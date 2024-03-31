package com.arrudeia.core.domain

import android.net.Uri
import com.arrudeia.core.data.entity.DataStoreUserRepositoryEntity
import com.arrudeia.core.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.data.network.DataStoreUserRepositoryImpl
import com.arrudeia.core.data.network.FirebaseUserRepositoryImpl
import com.arrudeia.core.data.network.ProfileRepositoryImpl
import com.arrudeia.core.entity.UserPersonalInformationUseCaseEntity
import javax.inject.Inject

class UpdateUserPersonalInformationUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val repositoryDataStore: DataStoreUserRepositoryImpl,
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

    private fun UserPersonalInformationUseCaseEntity.toDataStoreRepositoryEntity(): DataStoreUserRepositoryEntity {
        return DataStoreUserRepositoryEntity(
            uid = this.uuid.orEmpty(),
            name = this.name.orEmpty(),
            email = this.email.orEmpty(),
            image = this.profileImage.orEmpty(),
        )
    }

}
