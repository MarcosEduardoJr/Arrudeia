package com.arrudeia.core.data.repository

import com.arrudeia.core.data.interactions.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface FirebaseUserRepository {


    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ) : FirebaseUserRepositoryEntity?


    suspend fun signUserWithEmailAndPassword(
        email: String,
        password: String
    ) : FirebaseUserRepositoryEntity?

}