package com.arrudeia.core.run.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseRunMapScreenshotRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : FirebaseRunMapScreenshotRepository {

    override suspend fun saveMapScreenShot(image: ByteArray): String? {
        val storageRef = Firebase.storage.reference
        val imagesRef =
            storageRef.child(FOLDER_IMAGE_USER+"/${auth.currentUser?.uid}")
        val imageName =  "${UUID.randomUUID().toString()}.jpg"
        val uploadTask =  imagesRef.child(imageName).putBytes(image)
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
        const val FOLDER_IMAGE_USER = "run_trail_screenshot"
    }


}


