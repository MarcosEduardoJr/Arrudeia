package com.arrudeia.feature.services.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServiceUserUseCaseEntity(
    var uuid_user_creator: String?,
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
    val image: List<ServiceUserImageUseCase>?
)

@Serializable
data class ServiceUserImageUseCase(
    val id: Int,
    val service_id: Int,
    val url: String,
)

