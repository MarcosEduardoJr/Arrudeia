package com.arrudeia.core.data.repository

import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.result.Result


interface CepByZipCodeRepository {
    suspend fun getAddressByZipCode(zipCode : String): Result<CepAddressRepositoryEntity?>
}
