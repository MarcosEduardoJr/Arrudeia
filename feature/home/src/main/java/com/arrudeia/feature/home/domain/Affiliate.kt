package com.arrudeia.feature.home.domain

import com.arrudeia.core.designsystem.R.drawable.expedia as ExpediaIcon
import com.arrudeia.core.designsystem.R.drawable.hoteis_affiliate as HoteisIcon

enum class Affiliate(
    val icon: Int,
) {
    EXPEDIA(
        icon = ExpediaIcon,
    ),
    HOTELS(
        icon = HoteisIcon,
    ),
}
