package com.arrudeia.core.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume


class FirebaseUserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : FirebaseUserRepository {


    override suspend fun saveUserDocImage(uri: Uri): String {
        val storageRef = Firebase.storage.reference
        val uuid = firebaseAuth.currentUser?.uid.orEmpty()
        val imagesRef =
            storageRef.child(FOLDER_IMAGE_DOC)
        val imageName = "$uuid.jpg"

        val uploadTask = uri.let { imagesRef.child(imageName).putFile(it) }
        return suspendCancellableCoroutine { continuation ->
            uploadTask.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    continuation.resume(uri.toString())
                }
            }.addOnFailureListener { exception ->
                continuation.resume(String())
            }
        }
    }

    override suspend fun getUserImage(uuid: String): String? {
        val storageRef = Firebase.storage.reference
        val uuid = firebaseAuth.currentUser?.uid.orEmpty()
        val imagesRef =
            storageRef.child(FOLDER_IMAGE_USER + "/$uuid.jpg")
        val imgUrl = imagesRef.downloadUrl.await().toString()
        return try {
            imgUrl
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        const val FOLDER_IMAGE_DOC = "profile_image_doc"
        const val FOLDER_IMAGE_USER = "profile_image_user"
    }
}


