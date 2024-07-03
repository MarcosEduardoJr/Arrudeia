package com.arrudeia.core.data.repository

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.common.R
import com.arrudeia.core.data.repository.entity.UserPersonalInformationRepositoryEntity
import com.arrudeia.core.graphql.GetUserGraphQuery
import com.arrudeia.core.result.Result
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ProfileRepository {
    override suspend fun getUserPersonalInformationDetails(uuid: String):
            Result<UserPersonalInformationRepositoryEntity> {
        val response = apolloClient.query(GetUserGraphQuery(uuid)).execute()
        if (response.hasErrors() || response.data?.user == null)
            return Result.Error(R.string.error_try_again_later)
        return Result.Success(response.data!!.user!!.toEntity())
    }


}


