package com.arrudeia.feature.social.domain


import com.arrudeia.core.data.repository.ProfileDataStoreUserRepositoryImpl
import com.arrudeia.feature.social.data.TravelerRepositoryImpl
import javax.inject.Inject

class UpdateTravelersUseCase @Inject constructor(
    private val repository: TravelerRepositoryImpl,
    private val repositoryDataStore: ProfileDataStoreUserRepositoryImpl,
) {
    suspend operator fun invoke(
        travelerReceive: String,
        travelerReceiveMatch: String,
        travelerSend: String,
        travelerSendMatch: String,
    ) =
        repository.updateUserAboutMe(
            travelerReceive,
            travelerReceiveMatch,
            travelerSend,
            travelerSendMatch
        )

}