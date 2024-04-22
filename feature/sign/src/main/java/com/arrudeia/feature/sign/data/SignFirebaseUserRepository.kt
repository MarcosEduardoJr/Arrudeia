package com.arrudeia.feature.sign.data

import android.net.Uri
import com.arrudeia.core.data.entity.FirebaseUserRepositoryEntity
import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity

interface SignFirebaseUserRepository {


    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) : SignFirebaseUserRepositoryEntity?


    suspend fun signUserWithEmailAndPassword(
        email: String,
        password: String
    ) : SignFirebaseUserRepositoryEntity?

}