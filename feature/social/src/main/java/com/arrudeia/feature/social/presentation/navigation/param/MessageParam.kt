package com.arrudeia.feature.social.presentation.navigation.param

import kotlinx.serialization.Serializable

@Serializable
data class MessageParam(
  val  chatRoomUUID: String = "",
  val  opponentUUID: String = "",
  val  registerUUID: String = "",
)