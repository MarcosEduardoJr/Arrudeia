package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity

interface HomeProfileRepository {

    suspend fun getUserPersonalInformationDetails(uuid: String): UserPersonalInformationRepositoryEntity?

    suspend fun getUserAddress(uuid: String): UserAddressRepositoryEntity?
    suspend fun createUser(userInput: UserPersonalInformationRepositoryEntity): String?
    suspend fun updateUserPersonalInformation( userInput: UserPersonalInformationRepositoryEntity): String?

    suspend fun updateUserAddress( userInput: UserAddressRepositoryEntity): String?
}