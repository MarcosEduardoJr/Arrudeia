package com.arrudeia.core.data.repository.map

import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.graphql.GetCepAddressQuery


fun GetCepAddressQuery.CepAddress.toCepAddressRepositoryEntity(): CepAddressRepositoryEntity {
    return CepAddressRepositoryEntity(
        city = city,
        country = country,
        district = district,
        state = state,
        street = street,
        zipCode = zipCode
    )
}