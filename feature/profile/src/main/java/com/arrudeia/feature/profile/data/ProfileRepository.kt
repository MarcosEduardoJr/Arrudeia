package com.arrudeia.feature.profile.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.data.entity.UserAboutMeEntity
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity

interface ProfileRepository {

    suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity?>

    suspend fun getUserAddress(uuid: String): Result<UserAddressRepositoryEntity?>
    suspend fun updateUserPersonalInformation(userInput: UserPersonalInformationRepositoryEntity): Result<String?>

    suspend fun getUserAboutMe(uuid: String): Result<UserAboutMeEntity>
    suspend fun updateUserAddress(userInput: UserAddressRepositoryEntity): Result<String?>
    suspend fun updateUserAboutMe(
        uuid: String,
        interests: String,
        biography: String
    ): Result<String?>

}