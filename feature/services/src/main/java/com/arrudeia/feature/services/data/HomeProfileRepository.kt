package com.arrudeia.feature.services.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.entity.UserPersonalInformationRepositoryEntity

interface HomeProfileRepository {
    suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity?>
}