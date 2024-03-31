package com.arrudeia.core.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserPersonalInformationUseCaseEntity(
    var uuid: String? = "",
    var name: String? = "",
    var email: String? = "",
    var phone: String? = "",
    var idDocument: String? = "",
    var birthDate: String? = "",
    var profileImage: String? = "",
)
