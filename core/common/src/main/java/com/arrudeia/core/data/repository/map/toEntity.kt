package com.arrudeia.core.data.repository.map

import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.graphql.GetCepAddressQuery
import com.arrudeia.core.graphql.GetKeysQuery


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

fun List<GetKeysQuery.Key?>?.toMapKeyRepositoryEntity(): MutableMap<String, String> {
    var mapKeys: MutableMap<String, String> = mutableMapOf()
    this?.let {
        it.forEach { item ->
            mapKeys[item?.name.orEmpty()] = item?.key.orEmpty()
        }
    }
    return mapKeys
}