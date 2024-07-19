package com.arrudeia.feature.receipt.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptUseCase(
    val uuid: Int?,
    val name: String?,
    val time: Int?,
    val quantity: String?,
    val img: String?,
    val level: String?,
)
