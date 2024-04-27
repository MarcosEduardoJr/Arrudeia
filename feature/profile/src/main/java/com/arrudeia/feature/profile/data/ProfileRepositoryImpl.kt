package com.arrudeia.feature.profile.data

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.graphql.GetUserAddressGraphQuery
import com.arrudeia.core.graphql.GetUserGraphQuery
import com.arrudeia.core.graphql.UpdateUserAddressGraphMutation
import com.arrudeia.core.graphql.UpdateUserPersonalInfoGraphMutation
import com.arrudeia.core.result.Result
import com.arrudeia.feature.profile.R
import com.arrudeia.feature.profile.data.entity.UserAddressRepositoryEntity
import com.arrudeia.feature.profile.data.entity.UserPersonalInformationRepositoryEntity
import javax.inject.Inject


class ProfileRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ProfileRepository {
    override suspend fun getUserPersonalInformationDetails(uuid: String): Result<UserPersonalInformationRepositoryEntity> {
        val response = apolloClient.query(GetUserGraphQuery(uuid)).execute()
        if (response.hasErrors() || response.data?.user == null)
            return Result.Error(R.string.error_get_user)
        return Result.Success(response.data!!.user!!.toEntity())
    }

    override suspend fun getUserAddress(uuid: String): Result<UserAddressRepositoryEntity?> {
        val response = apolloClient.query(GetUserAddressGraphQuery(uuid)).execute()
        if (response.hasErrors())
            return Result.Error(R.string.error_get_user)
        return Result.Success(response.data?.user?.toAddressEntity())
    }

    override suspend fun updateUserPersonalInformation(
        userInput: UserPersonalInformationRepositoryEntity
    ): Result<String?> {
        val response = apolloClient.mutation(
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
        if (response.hasErrors())
            return Result.Error(R.string.error_update_user)
        return Result.Success(response.data?.updateUser.orEmpty())
    }

    override suspend fun updateUserAddress(userInput: UserAddressRepositoryEntity): Result<String?> {
        val response = apolloClient.mutation(
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
        if (response.hasErrors())
            return Result.Error(R.string.error_update_user)
        return Result.Success(response.data?.updateUser.orEmpty())
    }
}


