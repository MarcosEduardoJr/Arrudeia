package com.arrudeia.core.run.data

interface FirebaseRunMapScreenshotRepository  {

    suspend fun saveMapScreenShot(
        image: ByteArray
    ): String?
}