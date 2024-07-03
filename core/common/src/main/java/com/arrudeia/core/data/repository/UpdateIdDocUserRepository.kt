package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.result.Result


interface UpdateIdDocUserRepository {
    suspend fun updateIdDocUser(urlImgDoc: String): Result<String?>
}
