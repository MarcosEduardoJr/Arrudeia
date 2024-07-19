package com.arrudeia.feature.services.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ServiceUIModel(
    val id: Int?,
    val categoryId: Int?,
    val name: String?,
    val description: String?,
    val city: String?,
    val country: String?,
    val imageUrl: String?,
)

@Serializable
data class ServiceDetailUiModel(
    val categoryId: Int?,
    val city: String?,
    val country: String?,
    val description: String?,
    val district: String?,
    val id: Int?,
    val image: List<ServiceImageDetailUiModel?>,
    val name: String?,
    val number: Int?,
    val state: String?,
    val street: String?,
    val uuidUserCreator: String?,
    val zipCode: String?,
)


@Serializable
data class ServiceImageDetailUiModel(
    val id: Int?,
    val serviceId: Int?,
    val url: String?,
)