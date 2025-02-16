package com.arrudeia.core.location.util

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

fun getCurrentCityCountry(context: Context, callback: (String?, String?) -> Unit) {

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val locationTask: Task<Location> = fusedLocationClient.lastLocation
    locationTask.addOnSuccessListener { location: Location? ->
        location?.let {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val cityName = addresses[0].subAdminArea.orEmpty()
                callback(cityName, addresses[0].countryCode.orEmpty())
            } else {
                callback(null, null)
            }
        } ?: callback(null, null)
    }.addOnFailureListener {
        callback(null, null)
    }
}