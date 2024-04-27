package com.arrudeia.feature.profile.data

import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.result.Result
interface ProfileRepository {

    suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity?>

    suspend fun getUserAddress(uuid: String): Result<UserAddressRepositoryEntity?>
    suspend fun updateUserPersonalInformation( userInput: UserPersonalInformationRepositoryEntity): Result<String?>


    suspend fun updateUserAddress( userInput: UserAddressRepositoryEntity): Result<String?>

}