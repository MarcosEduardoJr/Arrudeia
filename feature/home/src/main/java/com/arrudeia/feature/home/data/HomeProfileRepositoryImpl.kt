package com.arrudeia.feature.home.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetUserGraphQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.home.R
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity
import javax.inject.Inject

class HomeProfileRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : HomeProfileRepository {

    override suspend fun getUserPersonalInformationDetails(uuid: String):
            Result<UserPersonalInformationRepositoryEntity?> {
        val response = apolloClient.query(GetUserGraphQuery(uuid)).execute()
        if (response.hasErrors())
            return Result.Error(R.string.error_get_user)
        return Result.Success(response.data?.user?.toEntity())
    }
}


