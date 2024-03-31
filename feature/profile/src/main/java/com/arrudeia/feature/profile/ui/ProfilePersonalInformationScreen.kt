package com.arrudeia.feature.profile.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.drawable.ic_calendar_event
import com.arrudeia.core.designsystem.R.drawable.ic_document
import com.arrudeia.core.designsystem.R.drawable.ic_email
import com.arrudeia.core.designsystem.R.drawable.ic_person
import com.arrudeia.core.designsystem.R.drawable.ic_smartphone
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.feature.profile.CameraPreview
import com.arrudeia.feature.profile.viewmodel.ProfilePersonalInformationViewModel
import com.arrudeia.feature.profile.ui.CAMERAX_UTIL.Companion.CAMERAX_PERMISSIONS
import com.arrudeia.feature.profile.viewmodel.ProfilePersonalInformationViewModel.PersonalInformationUiState

import com.arrudeia.feature.profile.viewmodel.ProfilePersonalInformationViewModel.PersonalInformationUpdateUserUiState
import com.arrudeia.feature.profile.R

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

@Composable
fun ProfilePersonalInformationRoute(
    onBackClick: () -> Unit,
    viewModel: ProfilePersonalInformationViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    viewModel.getUserPersonalInformation()

    var showDialogChangePhoto by rememberSaveable { mutableStateOf(false) }
    var nameValue by rememberSaveable { mutableStateOf("") }
    var docIdValue by rememberSaveable { mutableStateOf("") }
    var emailValue by rememberSaveable { mutableStateOf("") }
    var phoneValue by rememberSaveable { mutableStateOf("") }
    var birthDateValue by rememberSaveable { mutableStateOf("") }
    var profileImageValue by rememberSaveable { mutableStateOf("") }
    var showForm by rememberSaveable { mutableStateOf(false) }

    if (showDialogChangePhoto)
        ImageSelectionScreen(
            viewModel = viewModel,
            { showDialogChangePhoto = it }
        )
    else

        ScreenView(
            viewModel,
            { showDialogChangePhoto = it },
            nameValue,
            { nameValue = it },
            docIdValue,
            { docIdValue = it },
            emailValue,
            { emailValue = it },
            phoneValue,
            { phoneValue = it },
            birthDateValue,
            { birthDateValue = it },
            showForm,
            { showForm = it },
            viewModel.uri.value ?: profileImageValue,
            { profileImageValue = it },
            onShowSnackbar
        )

}


@Composable
fun form(
    name: String, onNameChange: (String) -> Unit,
    docId: String, onDocIdChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    phone: String, onPhoneChange: (String) -> Unit,
    birthDate: String, onBirthDateChange: (String) -> Unit,
) {

    TextFieldInput(
        hint = stringResource(id = R.string.full_name),
        name,
        icon = painterResource(id = ic_person),
        onValueChange = onNameChange,
        KeyboardType.Text,
        ImeAction.Next
    )
    Spacer(modifier = Modifier.size(4.dp))

    TextFieldInput(
        hint = stringResource(id = R.string.document_id),
        docId,
        icon = painterResource(id = ic_document),
        onValueChange = onDocIdChange,
        KeyboardType.Phone,
        ImeAction.Next
    )
    Spacer(modifier = Modifier.size(4.dp))

    TextFieldInput(
        hint = stringResource(id = R.string.email),
        email,
        icon = painterResource(id = ic_email),
        onValueChange = onEmailChange,
        KeyboardType.Email,
        ImeAction.Next
    )
    Spacer(modifier = Modifier.size(4.dp))

    TextFieldInput(
        hint = stringResource(id = R.string.phone),
        phone,
        icon = painterResource(id = ic_smartphone),
        onValueChange = onPhoneChange,
        KeyboardType.Phone,
        ImeAction.Next
    )
    Spacer(modifier = Modifier.size(4.dp))

    TextFieldInput(
        hint = stringResource(id = R.string.birth_date),
        birthDate,
        icon = painterResource(id = ic_calendar_event),
        onValueChange = onBirthDateChange,
        KeyboardType.Number,
        ImeAction.Done
    )
}

@Composable
fun ScreenView(
    viewModel: ProfilePersonalInformationViewModel,
    showDialogChangePhotoChange: (Boolean) -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    docId: String,
    onDocIdChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    showForm: Boolean,
    onShowFormChange: (Boolean) -> Unit,
    profileImage: Any,
    onProfileImageChange: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    var updatingUser by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = background_grey_F7F7F9))
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {

            Spacer(modifier = Modifier.size(30.dp))

            header(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.size(30.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(rememberScrollState())
            ) {

                if (showForm)
                    profileShorDetail(
                        Modifier
                            .align(Alignment.Center),
                        profileImage,
                        showDialogChangePhoto = showDialogChangePhotoChange
                    )
            }

            Spacer(modifier = Modifier.size(40.dp))

            if (showForm)
                form(
                    name, onNameChange,
                    docId, onDocIdChange,
                    email, onEmailChange,
                    phone, onPhoneChange,
                    birthDate, onBirthDateChange,
                )
            else {
                fetchUser(
                    viewModel,
                    onNameChange,
                    onEmailChange,
                    onPhoneChange,
                    onDocIdChange,
                    onBirthDateChange,
                    onShowFormChange,
                    onProfileImageChange,
                    onShowSnackbar
                )
            }
        }
        var colorButton: Color
        var clickButton: () -> Unit

        if (updatingUser) {
            updateUser(
                viewModel = viewModel,
                name,
                email,
                phone,
                docId,
                birthDate,
                onShowSnackbar = onShowSnackbar,
                updateUser = { updatingUser = it }
            )
            colorButton = colorResource(background_grey_F7F7F9)
            clickButton = {}
        } else {
            colorButton = colorResource(colorPrimary)
            clickButton = {
                if (isFormValid())
                    updatingUser = true
            }
        }
        ArrudeiaButtonColor(
            onClick = clickButton,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colorButton = colorButton,
        ) {
            Text(
                text = stringResource(id = R.string.save),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
        }
    }
}


@Composable
private fun updateUser(
    viewModel: ProfilePersonalInformationViewModel,
    name: String,
    email: String,
    phone: String,
    docId: String,
    birthDate: String,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    updateUser: (Boolean) -> Unit,
) {

    viewModel.savePersonalInformation(
        name, docId, email, phone, birthDate
    )

    val uiState by viewModel.sharedFlowUpdateUser.collectAsStateWithLifecycle()
    when (uiState) {
        is PersonalInformationUpdateUserUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is PersonalInformationUpdateUserUiState.Error -> {
            val message =
                stringResource((uiState as PersonalInformationUpdateUserUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
            updateUser(false)
        }

        is PersonalInformationUpdateUserUiState.Success -> {
            val message =
                stringResource((uiState as PersonalInformationUpdateUserUiState.Success).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                    updateUser(false)
                }
            }
        }

    }
}

@Composable
private fun fetchUser(
    viewModel: ProfilePersonalInformationViewModel,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onDocIdChange: (String) -> Unit,
    onBirthDateChange: (String) -> Unit,
    onShowFormChange: (Boolean) -> Unit,
    onProfileImageChange: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    when (uiState) {
        is PersonalInformationUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is PersonalInformationUiState.Error -> {
            val message =
                stringResource((uiState as PersonalInformationUpdateUserUiState.Error).message)
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(message, "")
                }
            }
        }

        is PersonalInformationUiState.Success -> {
            val user = (uiState as PersonalInformationUiState.Success).data
            onNameChange(user.name.orEmpty())
            onEmailChange(user.email.orEmpty())
            onPhoneChange(user.phone.orEmpty())
            onDocIdChange(user.idDocument.orEmpty())
            onBirthDateChange(user.birthDate.orEmpty())
            onProfileImageChange(user.profileImage.orEmpty())
            onShowFormChange(true)
        }

    }
}


private fun isFormValid(): Boolean {
    return true
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun profileShorDetail(
    modifier: Modifier,
    image: Any,
    showDialogChangePhoto: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(percent = 50),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(184.dp)
                    .background(color = colorResource(id = colorPrimary))
                    .fillMaxSize()
            ) {
                GlideImage(
                    loading = placeholder(ColorDrawable(colorResource(id = background_grey_F7F7F9).toArgb())),
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                )
            }

        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialogChangePhoto(true) },
            text = stringResource(id = R.string.change_image),
            color = colorResource(id = colorPrimary),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun header(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        title(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun title(modifier: Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(R.string.profile),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectionScreen(
    viewModel: ProfilePersonalInformationViewModel,
    showDialogChangePhotoChange: (Boolean) -> Unit,
) {

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.onTakePhoto(it)
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
                            tint = colorResource(id = colorPrimary)
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
                            icon = Icons.Default.Photo
                        )

                        CircularIconButton(
                            onClick = {
                                showLoading = true
                                takePhoto(
                                    controller = controller,
                                    onPhotoTaken = viewModel::onTakePhoto,
                                    context = context,
                                    showDialogChangePhotoChange
                                )
                            },
                            icon = Icons.Default.PhotoCamera
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImageSelectionScreen(hiltViewModel(), {})
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

private const val PICK_IMAGE_REQUEST = 1

fun openGallery(activity: Activity) {
    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
}
