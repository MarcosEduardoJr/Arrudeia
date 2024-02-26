package com.arrudeia.core.data.network

import com.arrudeia.core.data.repository.FirebaseUserRepository
import com.arrudeia.core.data.repository.FirebaseUserRepositoryEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class FirebaseUserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : FirebaseUserRepository {

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUserRepositoryEntity? {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        if (firebaseUser != null)
                            continuation.resume(firebaseUser.mapToRepository())
                        else
                            continuation.resume(null)
                    } else {
                        continuation.resume(null)
                    }
                }
        }
    }

    override suspend fun signUserWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUserRepositoryEntity? {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        if (firebaseUser != null)
                            continuation.resume(firebaseUser.mapToRepository())
                        else
                            continuation.resume(null)
                    } else {
                        continuation.resume(null)
                    }
                }
        }
    }

    private fun FirebaseUser.mapToRepository(): FirebaseUserRepositoryEntity {
        return FirebaseUserRepositoryEntity(
            this.uid,
            this.displayName.orEmpty(),
            this.email.orEmpty()
        )
    }
}


