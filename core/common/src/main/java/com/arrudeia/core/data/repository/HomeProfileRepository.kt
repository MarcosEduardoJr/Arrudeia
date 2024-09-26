package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.result.Result
interface HomeProfileRepository {
    suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity?>
}