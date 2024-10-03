package com.arrudeia.feature.social.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.arrudeia.core.graphql.GetTravelersQuery
import com.arrudeia.core.graphql.UpdateTravelerConnectionMutation
import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.data.entity.TravelersEntity
import javax.inject.Inject


class TravelerRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : TravelerRepository {
    override suspend fun getTravelers(uuid: String, page: Int):
            Result<List<TravelersEntity>?> {
        val response = apolloClient.query(GetTravelersQuery(uuid, page = Optional.present(page))).execute()
        if (response.hasErrors() || response.data?.travelers == null)
            return Result.Error(null)
        return Result.Success(response.data!!.travelers!!.toEntity())
    }

    override suspend fun updateUserAboutMe(
        travelerReceive: String,
        travelerReceiveMatch: String,
        travelerSend: String,
        travelerSendMatch: String,
    ): String? {
        val response = apolloClient.mutation(
            UpdateTravelerConnectionMutation(
                travelerReceive = travelerReceive,
                travelerReceiveMatch = travelerReceiveMatch,
                travelerSend = travelerSend,
                travelerSendMatch = travelerSendMatch
            )
        ).execute()
        if (response.hasErrors())
            return null
        return response.data?.updateTravelerConnection.orEmpty()
    }

}


