package com.arrudeia.feature.receipt.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetRecipesQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.data.entity.ReceiptRepositoryEntity
import javax.inject.Inject


class ReceiptsRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ReceiptsRepository {
    override suspend fun getReceipts(): Result<List<ReceiptRepositoryEntity?>?> {
        val response = apolloClient.query(GetRecipesQuery()).execute()
        if (response.hasErrors() || response.data?.recipes.toRecipeRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.recipes!!.toRecipeRepositoryEntity())
    }

}


