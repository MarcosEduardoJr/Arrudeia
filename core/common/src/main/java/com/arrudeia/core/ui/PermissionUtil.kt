package com.arrudeia.core.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

fun ComponentActivity.shouldShowFineLocationPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= 23 &&  shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun ComponentActivity.shouldShowCoarseLocationPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= 23 &&  shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
}

fun ComponentActivity.showShouldLocationPermission(): Boolean {
    return this.shouldShowFineLocationPermissionRationale()  || this.shouldShowCoarseLocationPermissionRationale()
}


fun ComponentActivity.shouldShowNotificationPermissionRationale(): Boolean {
    return Build.VERSION.SDK_INT >= 33 &&
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasFineLocationPermission(): Boolean {
    return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}

fun Context.hasCoarseLocationPermission(): Boolean {
    return hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
}

fun Context.hasNotificationPermission(): Boolean {
    return if(Build.VERSION.SDK_INT >= 33) {
        hasPermission(Manifest.permission.POST_NOTIFICATIONS)
    } else true
}