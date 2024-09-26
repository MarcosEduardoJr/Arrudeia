package com.arrudeia.feature.social.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetTravelersQuery
import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.data.entity.TravelersEntity
import javax.inject.Inject


class TravelerRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : TravelerRepository {
    override suspend fun getTravelers(uuid: String):
            Result<List<TravelersEntity>?> {
        val response = apolloClient.query(GetTravelersQuery(uuid)).execute()
        if (response.hasErrors() || response.data?.travelers == null)
            return Result.Error(null)
        return Result.Success(response.data!!.travelers!!.toEntity())
    }

    /*
       override suspend fun updateUserAboutMe(
           uuid: String,
           interests: String,
           biography: String
       ): Result<String?> {
           val response = apolloClient.mutation(
               UpdateUserInterestMutation(
                   uuid,
                   interests,
                   biography
               )
           ).execute()
           if (response.hasErrors())
               return Result.Error(R.string.error_update_user)
           return Result.Success(response.data?.updateUser.orEmpty())
       }*/

}


