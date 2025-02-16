package com.arrudeia.core.location.data.repository

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.UpdateUserLastLocationMutation
import javax.inject.Inject


class LastLocationRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : LastLocationRepository {
    override suspend fun updateLastLocation(uuid: String, lastCity: String, lastCountry: String) {
        apolloClient.mutation(UpdateUserLastLocationMutation(uuid, lastCity, lastCountry)).execute()
    }


}


