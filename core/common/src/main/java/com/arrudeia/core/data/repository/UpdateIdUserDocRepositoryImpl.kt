package com.arrudeia.core.data.repository

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.data.repository.entity.CepAddressRepositoryEntity
import com.arrudeia.core.data.repository.map.toCepAddressRepositoryEntity
import com.arrudeia.core.graphql.GetCepAddressQuery
import com.arrudeia.core.graphql.UpdateIdUserDocGraphMutation
import com.arrudeia.core.result.Result
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class UpdateIdUserDocRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val firebaseAuth: FirebaseAuth
) : UpdateIdDocUserRepository {
    override suspend fun updateIdDocUser(urlImgDoc: String): Result<String?> {
        val response = apolloClient.mutation(
            UpdateIdUserDocGraphMutation(
                firebaseAuth.currentUser?.uid.orEmpty(),
                urlImgDoc
            )
        ).execute()
        if (response.hasErrors() || response.data?.updateUser == null)
            return Result.Error(null)
        return Result.Success(response.data!!.updateUser.orEmpty())
    }

}


