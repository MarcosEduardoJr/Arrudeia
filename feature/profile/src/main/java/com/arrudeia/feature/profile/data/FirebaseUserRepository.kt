package com.arrudeia.feature.profile.data

import android.net.Uri
import com.arrudeia.feature.profile.data.entity.FirebaseUserRepositoryEntity

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