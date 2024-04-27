package com.arrudeia.feature.home.data

import com.arrudeia.feature.home.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.result.Result
interface HomeProfileRepository {
    suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity?>
}