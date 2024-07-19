package com.arrudeia.core.data.repository.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserPersonalInformationRepositoryEntity(
    val uuid: String?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val idDocument: String?,
    val birthDate: String?,
    val profileImage: String?
)
