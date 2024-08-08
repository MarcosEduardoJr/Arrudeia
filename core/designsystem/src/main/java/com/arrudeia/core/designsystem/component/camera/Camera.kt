package com.arrudeia.core.designsystem.component.camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.camera.CAMERAX_UTIL.Companion.CAMERAX_PERMISSIONS
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID
import androidx.camera.view.CameraController
import androidx.camera.view.PreviewView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.compose.rememberLauncherForActivityResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectionScreen(
    fetchUri: (Uri) -> Unit,
    showDialogChangePhotoChange: (Boolean) -> Unit,
) {

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                fetchUri(it)
                showDialogChangePhotoChange(false)
            }
        }
    val context = LocalContext.current
    if (!hasRequiredPermissions(context)) {
        ActivityCompat.requestPermissions(
            context as Activity, CAMERAX_PERMISSIONS, 0
        )
    } else {
        val controller = remember {
            LifecycleCameraController(context).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE
                )
            }
        }
        val scaffoldState = rememberBottomSheetScaffoldState()
        var showLoading by rememberSaveable { mutableStateOf(false) }

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {

            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White)
            ) {

                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .fillMaxSize()
                )

                if (showLoading)
                    ArrudeiaLoadingWheel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    ) else {

                    IconButton(
                        onClick = {
                            controller.cameraSelector =
                                if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                    CameraSelector.DEFAULT_FRONT_CAMERA
                                } else CameraSelector.DEFAULT_BACK_CAMERA
                        },
                        modifier = Modifier
                            .offset(16.dp, 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cameraswitch,
                            contentDescription = "Switch camera",
                            tint = colorResource(id = R.color.colorPrimary)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        CircularIconButton(
                            onClick = {
                                galleryLauncher.launch("image/*")
                            },
                            icon = Icons.Default.Photo,
                            modifier = Modifier
                        )

                        CircularIconButton(
                            onClick = {
                                showLoading = true
                                takePhoto(
                                    controller = controller,
                                    onPhotoTaken = fetchUri,
                                    context = context,
                                    showDialogChangePhotoChange
                                )
                            },
                            icon = Icons.Default.PhotoCamera,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}


private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Uri) -> Unit,
    context: Context,
    showDialogChangePhotoChange: (Boolean) -> Unit
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                Log.e("Camera", "Sucesso")
                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                Log.e("Camera", "Sucesso matrix")
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
                Log.e("Camera", "Sucesso rotate")
                val uri = bitmapToUri(context, rotatedBitmap)
                uri?.let {
                    onPhotoTaken(it)
                }
                Log.e("Camera", "Sucesso onphotontaken")
                showDialogChangePhotoChange(false)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}

fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    var bitmapUri: Uri? = null
    try {
        val file = File(context.externalCacheDir, UUID.randomUUID().toString() + ".png")
        val outputStream: OutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        bitmapUri = Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bitmapUri
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

private fun hasRequiredPermissions(context: Context): Boolean {
    return CAMERAX_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

class CAMERAX_UTIL {
    companion object {
        val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }
}