package com.arrudeia.feature.home.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetPromotionsQuery
import com.arrudeia.feature.home.domain.entity.PromotionUseCaseEntity
import javax.inject.Inject


class PromotionRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : PromotionsRepository {
    override suspend fun getAllPromotions(): List<PromotionUseCaseEntity> {
        val response = apolloClient.query(GetPromotionsQuery()).execute()
        if (response.hasErrors() || response.data?.promotions.toEntity() == null)
            emptyList<PromotionUseCaseEntity>()
        return response.data!!.promotions!!.toEntity()
    }
}

private fun List<GetPromotionsQuery.Promotion?>?.toEntity(): List<PromotionUseCaseEntity> {
    return this?.map {
        val images = it?.images?.split(",")?.filter { it.isNotEmpty() }.orEmpty()
        PromotionUseCaseEntity(
            name = it?.name.orEmpty(),
            images = images,
            affiliate = it?.affiliate.orEmpty(),
            totalValue = it?.totalValue.orEmpty(),
            urlClick = it?.urlClick.orEmpty(),
            date = it?.date.orEmpty(),
        )
    } ?: emptyList()
}


