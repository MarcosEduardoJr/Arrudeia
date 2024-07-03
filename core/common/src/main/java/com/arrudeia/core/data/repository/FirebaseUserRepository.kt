package com.arrudeia.core.data.repository

import android.net.Uri

interface FirebaseUserRepository {

    suspend fun saveUserDocImage(
        uri: Uri
    ) : String
}