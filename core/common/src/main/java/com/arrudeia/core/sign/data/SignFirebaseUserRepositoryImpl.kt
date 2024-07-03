package com.arrudeia.core.sign.data

import com.arrudeia.feature.sign.data.entity.SignFirebaseUserRepositoryEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


class SignFirebaseUserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : SignFirebaseUserRepository {

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): SignFirebaseUserRepositoryEntity? {
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
    ): SignFirebaseUserRepositoryEntity? {
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



    private fun FirebaseUser.mapToRepository(): SignFirebaseUserRepositoryEntity {
        return SignFirebaseUserRepositoryEntity(
            this.uid,
            this.displayName.orEmpty(),
            this.email.orEmpty()
        )
    }

}


