package com.arrudeia.feature.services.domain

import com.arrudeia.core.data.repository.CepByZipCodeRepositoryImpl
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.ServiceDetailRepositoryImpl
import com.arrudeia.feature.services.domain.entity.ServiceDetailUseCaseEntity
import javax.inject.Inject

class GetAddressByZipCodeUseCase @Inject constructor(
    private val repository: CepByZipCodeRepositoryImpl,
) {
    suspend operator fun invoke(zipCode: String) =
        repository.getAddressByZipCode(zipCode).toCepAddressUseCaseEntity()
}
