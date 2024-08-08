package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.result.Result

interface ProfileRepository {

    suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity?>


}