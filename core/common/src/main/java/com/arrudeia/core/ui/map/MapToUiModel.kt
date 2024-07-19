package com.arrudeia.core.ui.map

import com.arrudeia.core.domain.entity.CepAddressUseCaseEntity
import com.arrudeia.core.ui.model.CepAddressUiModel

fun CepAddressUseCaseEntity.toCepAddressUiModel(): CepAddressUiModel {
    return CepAddressUiModel(
        zipCode = this.zipCode.orEmpty(),
        street = this.street.orEmpty(),
        district = this.district.orEmpty(),
        city = this.city.orEmpty(),
        state = this.state.orEmpty(),
        country = this.country.orEmpty()
    )
}
