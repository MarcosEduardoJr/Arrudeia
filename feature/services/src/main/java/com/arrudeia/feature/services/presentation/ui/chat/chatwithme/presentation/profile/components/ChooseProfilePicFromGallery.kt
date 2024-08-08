package com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.profile.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChooseProfilePicFromGallery(
    profilePictureUrlForCheck: String,
    size: Dp = 100.dp,
    onSelect: (Uri?) -> Unit = {},
) {
    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onSelect(uri)
    }

    Box(
        modifier = Modifier
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(key1 = imageUri) {
            if (imageUri != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    bitmap = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, imageUri)
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver, imageUri!!)
                    bitmap = ImageDecoder.decodeBitmap(source)
                }
            }
        }
        if (bitmap != null) {

            GlideImage(
                model = bitmap,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable {  launcher.launch("image/*") }
            )
        } else {
            if (profilePictureUrlForCheck != "") {

                GlideImage(
                    model = profilePictureUrlForCheck,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable {  launcher.launch("image/*") }
                )
            } else {


                GlideImage(
                    model = Icons.Rounded.Build,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable {  launcher.launch("image/*") }
                )


            }
        }
    }
}