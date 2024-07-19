package com.arrudeia.feature.aid.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetAidDetailQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.aid.data.entity.AidDetailRepositoryEntity
import javax.inject.Inject


class AidDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : AidDetailRepository {
    override suspend fun getAid(id: String): Result<AidDetailRepositoryEntity?> {
        val response = apolloClient.query(GetAidDetailQuery(id)).execute()
        if (response.hasErrors() || response.data?.aid.toAidDetailRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.aid!!.toAidDetailRepositoryEntity())
    }
}


