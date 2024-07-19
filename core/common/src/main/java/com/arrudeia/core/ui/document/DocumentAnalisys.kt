package com.arrudeia.core.ui.document

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.common.R.string.document_analisys_description
import com.arrudeia.core.common.R.string.document_analisys_error
import com.arrudeia.core.common.R.string.identification_document
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.camera.ImageSelectionScreen
import com.arrudeia.core.viewmodel.DocumentAnalysisViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@OptIn(ExperimentalGlideComposeApi::class)
@Suppress("LongMethod")
@Composable
fun DocumentAnalisys(
    viewModel: DocumentAnalysisViewModel = hiltViewModel(),
    showDocumentAnalysis: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    showBottomBar: (Boolean) -> Unit
) {
    showBottomBar(false)
    val context = LocalContext.current
    val imageList by remember { mutableStateOf(viewModel.uri) }
    var showCamera by remember { mutableStateOf(false) }
    var savedDocument by remember { mutableStateOf(false) }
    var showDocument by remember { mutableStateOf(true) }
    var resultDocumentAnalysis by remember { mutableStateOf<Boolean?>(null) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val toastMessage by remember { mutableStateOf(viewModel.toastMessage) }
    val isLoading by remember { mutableStateOf(viewModel.isLoading) }
    val savedDocumentUser by remember { mutableStateOf(viewModel.savedDocument) }




    LaunchedEffect(savedDocumentUser.value) {
        if (savedDocumentUser.value || !showDocument) {
            viewModel.reset()
            showDocumentAnalysis(false)
        }
    }





    if (toastMessage.value != 0)
        errorMessage = stringResource(viewModel.toastMessage.value)

    LaunchedEffect(isLoading.value) {
        isSaving = isLoading.value
    }

    LaunchedEffect(toastMessage.value) {
        if (toastMessage.value != 0) {
            onShowSnackbar(errorMessage, null)
            isSaving = false
        }
    }

    if (savedDocument) {
        showDocumentAnalysis(false)
    } else if (showCamera) {
        resultDocumentAnalysis = null
        ImageSelectionScreen(
            { viewModel.onTakePhoto(it) },
            { showCamera = it }
        )
    } else if (isSaving) {
        ArrudeiaLoadingWheel(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        )
    } else {
        processAnalysis(imageList, context, { resultDocumentAnalysis = it })
    }

    if (!showCamera && !isSaving) {
        ContentResultAnalysis(
            viewModel,
            resultDocumentAnalysis,
            showDocumentAnalysis,
            showCamera = { showCamera = it },
            onBackClick = { showDocument = it },
            sizeImages = imageList
        )
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ContentResultAnalysis(
    viewModel: DocumentAnalysisViewModel,
    resultDocumentAnalysis: Boolean?,
    showDocumentAnalysis: (Boolean) -> Unit,
    showCamera: (Boolean) -> Unit,
    onBackClick: (Boolean) -> Unit,
    sizeImages: List<Uri>,
) {
    val listImages by remember { mutableStateOf(viewModel.uri) }



    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center)
                .background(
                    if (listImages.isNotEmpty()) Color.White
                    else
                        colorResource(id = R.color.background_grey_F7F7F9)
                )
        ) {


            LazyColumn(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            ) {
                items(items = listImages, itemContent = {

                    Box(
                        modifier = Modifier
                            .background(
                                if (resultDocumentAnalysis == true || resultDocumentAnalysis == null)
                                    Color.Green
                                else Color.Red
                            )
                            .padding(4.dp)
                    ) {

                        GlideImage(
                            model = it,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        )
                        Icon(
                            Icons.Rounded.Close, null, modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    viewModel.removeImage(it)
                                },
                            tint = colorResource(id = R.color.colorPrimary)
                        )

                    }
                })

            }

            Spacer(
                modifier = Modifier
                    .size(60.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            CircularIconButton(
                onClick = {
                    showDocumentAnalysis(false)
                },
                icon = Icons.Rounded.ArrowBack,
                backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
                iconSize = 50.dp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Center),
                text = stringResource(identification_document),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 36.dp, vertical = 60.dp),
            text = stringResource(
                if (resultDocumentAnalysis == true || resultDocumentAnalysis == null || sizeImages.isEmpty())
                    document_analisys_description
                else document_analisys_error
            ),
            color = if (resultDocumentAnalysis == true || resultDocumentAnalysis == null || sizeImages.isEmpty())
                Color.Gray
            else
                Color.Red,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        ArrudeiaButtonColor(
            onClick = {
                if (!viewModel.isLoading.value) {
                    if (resultDocumentAnalysis == true) {
                        viewModel.saveDocument()
                    } else {
                        viewModel.clearImages()
                        showCamera(true)
                    }
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colorButton = colorResource(id = R.color.colorPrimary),
        ) {
            Text(
                text = if (resultDocumentAnalysis == true) "Salvar" else "Abrir a camera",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
            )
        }
    }
}


private fun processAnalysis(
    imageList: List<Uri>,
    context: Context,
    result: (Boolean) -> Unit
) {
    if (imageList.isNotEmpty()) {

        val bitmap: Bitmap = uriToBitmap(context, imageList[0])
        val image = InputImage.fromBitmap(bitmap, 0)

        val recognizer =
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                if (visionText.textBlocks.isEmpty())
                    result(false)
                else
                    result(extractAndValidateCPF(visionText.text))
            }
            .addOnFailureListener { e ->
                result(false)

            }
    }
}


fun validateIdentifier(identifier: String): Boolean {
    val cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}\$".toRegex()

    return when {
        cpfRegex.matches(identifier) -> true
        else -> false
    }
}

fun extractAndValidateCPF(input: String): Boolean {
    val cpfRegex = "\\d{11}".toRegex()
    val matchResult = cpfRegex.find(input)

    return if (matchResult != null) {
        val cpf = matchResult.value
        val formattedCPF = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(
            6,
            9
        ) + "-" + cpf.substring(9, 11)
        validateIdentifier(formattedCPF)
    } else {
        false
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
}