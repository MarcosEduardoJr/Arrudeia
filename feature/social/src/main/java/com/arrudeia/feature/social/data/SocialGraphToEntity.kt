package com.arrudeia.feature.social.data

import com.arrudeia.core.graphql.GetTravelersQuery
import com.arrudeia.feature.social.data.entity.TravelersEntity


fun List<GetTravelersQuery.Traveler?>?.toEntity(): List<TravelersEntity>? {
    return this?.let { list ->
        list.map {
            TravelersEntity(
                uuid = it?.uuid.orEmpty(),
                name = it?.name.orEmpty(),
                img = it?.img,
                city = it?.city.orEmpty(),
                interests = it?.interests.orEmpty(),
                aboutMe = it?.aboutMe.orEmpty(),
                gender = it?.gender.orEmpty()
            )
        }
    }
}