package com.arrudeia.core.places.data

import android.net.Uri

interface FirebaseArrudeiaMapRepository {

    suspend fun savePlaceImage(
        namePlace:String,
        uri: Uri
    ): String?
}