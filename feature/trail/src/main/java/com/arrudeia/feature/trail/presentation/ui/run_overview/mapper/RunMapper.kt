package com.arrudeia.feature.trail.presentation.ui.run_overview.mapper

import com.arrudeia.core.designsystem.component.util.formatted
import com.arrudeia.core.designsystem.component.util.toFormattedHeartRate
import com.arrudeia.core.designsystem.component.util.toFormattedKm
import com.arrudeia.core.designsystem.component.util.toFormattedKmh
import com.arrudeia.core.designsystem.component.util.toFormattedMeters
import com.arrudeia.core.designsystem.component.util.toFormattedPace
import com.arrudeia.core.run.domain.Run
import com.arrudeia.feature.trail.presentation.ui.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl,
        avgHeartRate = avgHeartRate.toFormattedHeartRate(),
        maxHeartRate = maxHeartRate.toFormattedHeartRate()
    )
}