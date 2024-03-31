package com.arrudeia.core.data.network

import android.net.Uri
import com.arrudeia.core.data.repository.FirebaseUserRepository
import com.arrudeia.core.data.entity.FirebaseUserRepositoryEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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

    override suspend fun saveUserImage(uri: Uri): String? {
        val storageRef = Firebase.storage.reference
        val uuid = firebaseAuth.currentUser?.uid.orEmpty()
        val imagesRef =
            storageRef.child(FOLDER_IMAGE_USER)
        val imageName = "$uuid.jpg"

        val uploadTask = uri.let { imagesRef.child(imageName).putFile(it) }
        return suspendCancellableCoroutine { continuation ->
            uploadTask.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    continuation.resume(uri.toString())
                }
            }.addOnFailureListener { exception ->
                continuation.resume(null)
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

    companion object {
        const val FOLDER_IMAGE_USER = "profile_image_user"
    }
}


