package com.arrudeia.feature.services.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetServiceQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.entity.ServiceDetailRepositoryEntity
import javax.inject.Inject


class ServiceDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ServiceDetailRepository {
    override suspend fun getService(id: Int): Result<ServiceDetailRepositoryEntity?> {
        val response = apolloClient.query(GetServiceQuery(id)).execute()
        if (response.hasErrors() || response.data?.service.toServiceDetailRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.service!!.toServiceDetailRepositoryEntity())
    }

}


