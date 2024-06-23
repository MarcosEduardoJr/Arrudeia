package com.arrudeia.feature.services.presentation.navigation.param

import kotlinx.serialization.Serializable
@Serializable
data class ChatParam(
    val chatRoomUUID: String = "",
    val opponentUUID: String = "",
    val registerUUID: String = ""
)