package com.arrudeia.feature.profile.data

import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity

interface ProfileRepository {

    suspend fun getUserPersonalInformationDetails(uuid: String): UserPersonalInformationRepositoryEntity?

    suspend fun getUserAddress(uuid: String): UserAddressRepositoryEntity?
    suspend fun updateUserPersonalInformation( userInput: UserPersonalInformationRepositoryEntity): String?


    suspend fun updateUserAddress( userInput: UserAddressRepositoryEntity): String?

}