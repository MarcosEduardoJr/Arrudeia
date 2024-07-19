package com.arrudeia.feature.services.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.CreateServiceMutation
import com.arrudeia.core.graphql.GetServicesExpertiseQuery
import com.arrudeia.core.graphql.GetServicesQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.services.data.entity.ServiceExpertiseRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceRepositoryEntity
import com.arrudeia.feature.services.data.entity.ServiceUserRepositoryEntity
import javax.inject.Inject


class ServiceRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ServiceRepository {
    override suspend fun getServices(): Result<List<ServiceRepositoryEntity?>?> {
        val response = apolloClient.query(GetServicesQuery()).execute()
        if (response.hasErrors() || response.data?.services.toServicesRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.services!!.toServicesRepositoryEntity())
    }

    override suspend fun getServicesCategoriesExpertise(): Result<List<ServiceExpertiseRepositoryEntity>> {
        val response = apolloClient.query(GetServicesExpertiseQuery()).execute()
        if (response.hasErrors() || response.data?.servicesExpertise?.toServicesExpertiseRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.servicesExpertise!!.toServicesExpertiseRepositoryEntity())
    }

    override suspend fun createService(service: ServiceUserRepositoryEntity): Result<Int?> {
        val response =
            apolloClient.mutation(CreateServiceMutation(service.toServiceUserRepositoryEntity()))
                .execute()
        if (response.hasErrors() || response.data?.createServiceCategory == null)
            return Result.Error(null)
        return Result.Success(response.data!!.createServiceCategory)
    }
}


