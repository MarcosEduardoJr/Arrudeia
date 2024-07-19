package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.CepByZipCodeRepositoryImpl
import javax.inject.Inject

class GetAddressByZipCodeUseCase @Inject constructor(
    private val repository: CepByZipCodeRepositoryImpl,
) {
    suspend operator fun invoke(zipCode: String) =
        repository.getAddressByZipCode(zipCode).toCepAddressUseCaseEntity()
}
