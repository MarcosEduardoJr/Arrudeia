package com.arrudeia.feature.services.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class NewServiceUserUiModel(
    val uuid_user_creator: String?,
    val name: String?,
    val description: String?,
    val zip_code: String?,
    val street: String?,
    val number: Int?,
    val district: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val category_id: Int?,
    var image: List<NewServiceUserImageUiModel>
)

@Serializable
data class NewServiceUserImageUiModel(
    val id: Int,
    val service_id: Int,
    val url: String,
)

