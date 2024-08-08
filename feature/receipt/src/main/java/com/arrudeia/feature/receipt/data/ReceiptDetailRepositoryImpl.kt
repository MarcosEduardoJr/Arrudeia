package com.arrudeia.feature.receipt.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetRecipeDetailQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.receipt.data.entity.ReceiptDetailRepositoryEntity
import javax.inject.Inject


class ReceiptDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ReceiptDetailRepository {
    override suspend fun getReceipt(id: String): Result<ReceiptDetailRepositoryEntity?> {
        val response = apolloClient.query(GetRecipeDetailQuery(id)).execute()
        if (response.hasErrors() || response.data?.recipe.toReceiptDetailRepositoryEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.recipe!!.toReceiptDetailRepositoryEntity())
    }
}


