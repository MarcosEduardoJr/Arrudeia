package com.arrudeia.core.domain

import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.domain.entity.CepAddressUseCaseEntity
import com.arrudeia.core.result.Result
import com.arrudeia.core.common.R

fun Result<CepAddressRepositoryEntity?>.toCepAddressUseCaseEntity(): Result<CepAddressUseCaseEntity?> {
    var item: Result<CepAddressUseCaseEntity?> =
        Result.Error(R.string.erro_message_list_travels)
    when (this) {
        is Result.Success -> {
            this.data?.let {
                item = Result.Success(
                    CepAddressUseCaseEntity(
                        city = it.city.orEmpty(),
                        country = it.country.orEmpty(),
                        district = it.district.orEmpty(),
                        state = it.state.orEmpty(),
                        street = it.street.orEmpty(),
                        zipCode = it.zipCode.orEmpty(),
                    )
                )
            }
        }

        is Result.Error -> {
            item = Result.Error(this.message)
        }

        else -> {
            item = Result.Error(R.string.erro_message_list_travels)
        }
    }

    return item
}

