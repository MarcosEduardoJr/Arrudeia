package com.arrudeia.feature.aid.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class AidUIModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val img: String?,
    val urlVideo: String?,
)

@Serializable
data class AidDetailUiModel(
    val id: Int?,
    val description: String?,
    val img: String?,
    val steps: List<AidStepReceiptDetailUiModel?>?,
    val name: String?,
    val urlVideo: String?,
)


@Serializable
data class AidStepReceiptDetailUiModel(
    val id: Int?,
    val aidId: String?,
    val step: String?,
)