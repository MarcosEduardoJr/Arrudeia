package com.arrudeia.core.sign.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.arrudeia.core.graphql.CreateUserGraphMutation
import com.arrudeia.core.result.Result
import javax.inject.Inject


class CreateUserRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
) : CreateUserRepository {
    override suspend fun createUser(
        uuid: String,
        birthDate: String?,
        city: String?,
        country: String?,
        district: String?,
        email: String?,
        idDocument: String?,
        lastCity: String?,
        lastCountry: String?,
        name: String?,
        number: Int?,
        profileImage: String?,
        phone: String?,
        state: String?,
        street: String?,
        zipCode: String?,
    ): Result<String?> {
        val response = apolloClient.mutation(
            CreateUserGraphMutation(
                uuid,
                Optional.present(birthDate),
                Optional.present(city),
                Optional.present(country),
                Optional.present(district),
                Optional.present(email),
                Optional.present(idDocument),
                Optional.present(lastCity),
                Optional.present(lastCountry),
                Optional.present(name),
                Optional.present(number),
                Optional.present(profileImage),
                Optional.present(phone),
                Optional.present(state),
                Optional.present(street),
                Optional.present(zipCode)
            )
        ).execute()
        if (response.hasErrors() || response.data?.createUser == null)
            return Result.Error(null)
        return Result.Success(response.data!!.createUser.orEmpty())
    }

}


