package com.arrudeia.core.data.repository

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.data.repository.map.toCepAddressRepositoryEntity
import com.arrudeia.core.graphql.GetCepAddressQuery
import com.arrudeia.core.result.Result
import javax.inject.Inject


class CepByZipCodeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : CepByZipCodeRepository {
    override suspend fun getAddressByZipCode(zipCode: String): Result<CepAddressRepositoryEntity?> {
        val response = apolloClient.query(GetCepAddressQuery(zipCode)).execute()
        if (response.hasErrors() || response.data?.cepAddress?.toCepAddressRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.cepAddress!!.toCepAddressRepositoryEntity())
    }

}


