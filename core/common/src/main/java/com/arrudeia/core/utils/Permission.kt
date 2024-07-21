package com.arrudeia.core.utils

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.arrudeia.core.designsystem.component.ArrudeiaDialog
import com.arrudeia.core.designsystem.component.ArrudeiaOutlinedActionButton
import com.arrudeia.core.common.R.string.permission_required
import com.arrudeia.core.common.R.string.need_know_location_permission
import com.arrudeia.core.common.R.string.yes
import com.arrudeia.core.common.R.string.no
import com.arrudeia.core.ui.hasFineLocationPermission
import com.arrudeia.core.ui.hasNotificationPermission
import com.arrudeia.core.ui.showShouldLocationPermission

@Composable
fun ShowPermissionLocationDialog(
    needPermission: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        needPermission(false)
    }

    if (activity.showShouldLocationPermission()) {
        needPermission(true)
        ArrudeiaDialog(
            title = stringResource(id = permission_required),
            onDismiss = {
                needPermission(false)
            },
            description =
            stringResource(id = need_know_location_permission),
            primaryButton = {
                ArrudeiaOutlinedActionButton(
                    text = stringResource(id = yes),
                    isLoading = false,
                    onClick = {
                        permissionLauncher.requestArrudeiaPermissions(context)
                    },
                )
            },
            secondaryButton = {
                ArrudeiaOutlinedActionButton(
                    text = stringResource(id = no),
                    isLoading = false,
                    onClick = {
                        needPermission(false)
                    },
                )
            },
        )
    }
}


private fun ActivityResultLauncher<Array<String>>.requestArrudeiaPermissions(
    context: Context
) {
    val hasLocationPermission = context.hasFineLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions)
        }

        !hasLocationPermission -> launch(locationPermissions)

    }
}