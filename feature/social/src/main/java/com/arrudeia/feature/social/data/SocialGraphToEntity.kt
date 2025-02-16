package com.arrudeia.feature.social.data

import com.arrudeia.core.graphql.GetTravelersQuery
import com.arrudeia.feature.social.data.entity.TravelersEntity


fun List<GetTravelersQuery.Traveler?>?.toEntity(): List<TravelersEntity>? {
    return this?.let { list ->
        list.map {
            TravelersEntity(
                travelerReceiveId = it?.travelerReceiveId.orEmpty(),
                travelerSendId = it?.travelerSendId.orEmpty(),
                name = it?.name.orEmpty(),
                img = it?.img.orEmpty(),
                city = it?.city.orEmpty(),
                interests = it?.interests.orEmpty(),
                aboutMe = it?.aboutMe.orEmpty(),
                gender = it?.gender.orEmpty(),
                travelerReceiveMatch = it?.travelerReceiveMatch?:0,
                travelerSendMatch = it?.travelerSendMatch?:0,
                travelerReceiveEmail = it?.travelerReceiveEmail.orEmpty(),
                travelerSendEmail = it?.travelerSendEmail.orEmpty(),
            )
        }
    }
}