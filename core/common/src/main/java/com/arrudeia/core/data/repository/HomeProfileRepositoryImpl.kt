package com.arrudeia.core.data.repository

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.common.R.string.generic_error
import com.arrudeia.core.data.repository.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.graphql.GetUserGraphQuery
import com.arrudeia.core.result.Result
import javax.inject.Inject

class HomeProfileRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : HomeProfileRepository {

    override suspend fun getUserPersonalInformationDetails(uuid: String):
            Result<UserPersonalInformationRepositoryEntity?> {
        val response = apolloClient.query(GetUserGraphQuery(uuid)).execute()
        if (response.hasErrors())
            return Result.Error(generic_error)
        return Result.Success(response.data?.user?.toEntity())
    }
}


