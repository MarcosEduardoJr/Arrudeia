package com.arrudeia.feature.social.domain


import com.arrudeia.feature.social.data.TravelerRepositoryImpl
import javax.inject.Inject

class UpdateTravelersUseCase @Inject constructor(
    private val repository: TravelerRepositoryImpl
) {
    suspend operator fun invoke(
        travelerReceive: String,
        travelerSend: String,
        travelerReceiveMatch: Int,
        travelerSendMatch: Int,
    ) =
        repository.updateUserAboutMe(
            travelerReceive,
            travelerSend,
            travelerSendMatch,
            travelerReceiveMatch,
        )

}