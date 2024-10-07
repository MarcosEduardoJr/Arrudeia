package com.arrudeia.feature.social.data

import com.arrudeia.core.result.Result
import com.arrudeia.feature.social.data.entity.TravelersEntity

interface TravelerRepository {
    suspend fun getTravelers(uuid: String, page: Int) : Result<List<TravelersEntity>?>
    suspend fun updateUserAboutMe(
        travelerReceive: String,
        travelerSend: String,
        travelerSendMatch: Int,
        travelerReceiveMatch: Int,
    ):Result<String?>
}