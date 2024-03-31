package com.arrudeia.core.data.repository

import android.net.Uri
import com.arrudeia.core.data.entity.FirebaseUserRepositoryEntity

interface FirebaseUserRepository {


    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) : FirebaseUserRepositoryEntity?


    suspend fun signUserWithEmailAndPassword(
        email: String,
        password: String
    ) : FirebaseUserRepositoryEntity?

    suspend fun saveUserImage(
        uri: Uri
    ) : String?
}