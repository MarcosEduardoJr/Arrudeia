package com.arrudeia.feature.services.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseArrudeiaMapRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : FirebaseArrudeiaMapRepository {

    override suspend fun savePlaceImage(namePlace: String, uri: Uri): String? {
        val storageRef = Firebase.storage.reference
        val uuid = firebaseAuth.currentUser?.uid.orEmpty()
        val imagesRef =
            storageRef.child(FOLDER_IMAGE_USER)
        val imageName = uuid +"_"+"$namePlace.jpg"

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


    companion object {
        const val FOLDER_IMAGE_USER = "service_image"
    }


}


