package com.arrudeia.core.run.domain

import android.location.Location
import com.arrudeia.core.run.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location =  com.arrudeia.core.run.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}