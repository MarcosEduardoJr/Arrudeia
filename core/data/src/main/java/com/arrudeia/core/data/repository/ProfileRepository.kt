package com.arrudeia.core.data.repository

import com.arrudeia.core.data.entity.UserAddressRepositoryEntity
import com.arrudeia.core.data.entity.UserPersonalInformationRepositoryEntity

interface ProfileRepository {

    suspend fun getUserPersonalInformationDetails(uuid: String): UserPersonalInformationRepositoryEntity?

    suspend fun getUserAddress(uuid: String): UserAddressRepositoryEntity?
    suspend fun createUser(userInput: UserPersonalInformationRepositoryEntity): String?
    suspend fun updateUserPersonalInformation( userInput: UserPersonalInformationRepositoryEntity): String?

    suspend fun updateUserAddress( userInput: UserAddressRepositoryEntity): String?
    suspend fun deleteUser(uuid: String): Boolean
}