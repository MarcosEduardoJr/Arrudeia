package com.arrudeia.feature.checklist.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.CheckListGraphQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.checklist.data.entity.CheckListRepositoryEntity
import com.arrudeia.feature.checklist.data.entity.toEntity
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : CheckListRepository {
    override suspend fun getChecklist(): Result<List<CheckListRepositoryEntity>?> {
        val response = apolloClient.query(CheckListGraphQuery()).execute()
        if (response.hasErrors() || response.data?.checklist.toEntity() == null)
            return Result.Error(null)
        return Result.Success(response.data!!.checklist!!.toEntity())
    }
}


