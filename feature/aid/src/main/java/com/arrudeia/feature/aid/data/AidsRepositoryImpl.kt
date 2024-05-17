package com.arrudeia.feature.aid.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetAidsQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.data.entity.AidRepositoryEntity
import javax.inject.Inject


class AidsRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AidsRepository {
    override suspend fun getAids(): Result<List<AidRepositoryEntity?>?> {
        val response = apolloClient.query(GetAidsQuery()).execute()
        if (response.hasErrors() || response.data?.aids.toAidsRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.aids!!.toAidsRepositoryEntity())
    }

}


