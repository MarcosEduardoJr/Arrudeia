package com.arrudeia.feature.receipt.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptRepositoryEntity(
    val uuid: Int?,
    val name: String?,
    val time: Int?,
    val quantity: String?,
    val img: String?,
    val level: String?,
)
