package com.arrudeia.core.sign.data

import com.arrudeia.core.result.Result


interface CreateUserRepository {
    suspend fun createUser(
        uuid: String,
        birthDate: String? = null,
        city: String? = null,
        country: String? = null,
        district: String? = null,
        email: String? = null,
        idDocument: String? = null,
        lastCity: String? = null,
        lastCountry: String? = null,
        name: String? = null,
        number: Int? = null,
        profileImage: String? = null,
        phone: String? = null,
        state: String? = null,
        street: String? = null,
        zipCode: String? = null,

        ): Result<String?>
}
