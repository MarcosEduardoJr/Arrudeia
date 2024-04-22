package com.arrudeia.feature.home.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.arrudeia.core.data.GetUserAddressGraphQuery
import com.arrudeia.core.data.GetUserGraphQuery
import com.arrudeia.core.data.UpdateUserAddressGraphMutation
import com.arrudeia.core.data.UpdateUserPersonalInfoGraphMutation
import javax.inject.Inject
import com.arrudeia.feature.home.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.home.data.entity.UserPersonalInformationRepositoryEntity


class HomeProfileRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : HomeProfileRepository {


    override suspend fun getUserPersonalInformationDetails(uuid: String): UserPersonalInformationRepositoryEntity? {
        val response = try {
            apolloClient.query(GetUserGraphQuery(uuid)).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.user?.toEntity()
    }

    override suspend fun getUserAddress(uuid: String): UserAddressRepositoryEntity? {
        val response = try {
            apolloClient.query(GetUserAddressGraphQuery(uuid)).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.user?.toAddressEntity()
    }

    override suspend fun createUser(userInput: UserPersonalInformationRepositoryEntity): String? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserPersonalInformation(
        userInput: UserPersonalInformationRepositoryEntity
    ): String? {
        val response = try {
            apolloClient.mutation(
                UpdateUserPersonalInfoGraphMutation(
                    userInput.uuid.orEmpty(),
                    userInput.name.orEmpty(),
                    userInput.email.orEmpty(),
                    userInput.phone.orEmpty(),
                    userInput.idDocument.orEmpty(),
                    userInput.birthDate.orEmpty(),
                    userInput.profileImage.orEmpty()
                )
            ).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.updateUser.orEmpty()
    }

    override suspend fun updateUserAddress(userInput: UserAddressRepositoryEntity): String? {
        val response = try {
            apolloClient.mutation(
                UpdateUserAddressGraphMutation(
                    userInput.uuid.orEmpty(),
                    userInput.zipCode.orEmpty(),
                    userInput.street.orEmpty(),
                    userInput.number ?: 0,
                    userInput.district.orEmpty(),
                    userInput.city.orEmpty(),
                    userInput.state.orEmpty(),
                    userInput.country.orEmpty()
                )
            ).execute()
        } catch (e: ApolloException) {
            return null
        }
        return response.data?.updateUser.orEmpty()
    }


}


