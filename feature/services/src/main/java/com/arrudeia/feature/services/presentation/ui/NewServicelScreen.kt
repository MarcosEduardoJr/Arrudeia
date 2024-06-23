package com.arrudeia.feature.services.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.component.DropDown
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.core.designsystem.component.TextFieldInput
import com.arrudeia.core.designsystem.component.camera.ImageSelectionScreen
import com.arrudeia.core.ui.address.ArrudeiaddressForm
import com.arrudeia.feature.services.presentation.model.NewServiceUserUiModel
import com.arrudeia.feature.services.presentation.viewmodel.NewServiceUiState
import com.arrudeia.feature.services.presentation.viewmodel.NewServiceViewModel
import com.arrudeia.feature.services.presentation.viewmodel.ServiceExpertiseUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.arrudeia.feature.services.R.string.fill_correct_field
import com.arrudeia.feature.services.R.string.title
import com.arrudeia.feature.services.R.string.description
import com.arrudeia.feature.services.R.string.category
import com.arrudeia.feature.services.R.string.photo

@Composable
internal fun NewServiceRoute(
    viewModel: NewServiceViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    var zipCode by rememberSaveable { mutableStateOf("") }
    var street by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var district by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }


    var showForm by rememberSaveable { mutableStateOf(false) }
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var listDrop by rememberSaveable { mutableStateOf(listOf<DropListUiModel>()) }
    var dropChoosed by remember {
        mutableStateOf<DropListUiModel>(
            DropListUiModel()
        )
    }
    var showCamera by rememberSaveable { mutableStateOf(false) }

    var saveForm by rememberSaveable { mutableStateOf(false) }

    val savedForm by viewModel.sharedFlow.collectAsStateWithLifecycle()

    when (savedForm) {
        is NewServiceUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
            )
        }

        is NewServiceUiState.Error -> {
            val message = stringResource((savedForm as NewServiceUiState.Error).message)
            LaunchedEffect(true) {
                onShowSnackbar(message, "")
            }

            saveForm = false
        }

        is NewServiceUiState.Success -> {
            onBackClick()
        }
    }
    if (listDrop.isEmpty())
        fetchData(viewModel, { listDrop = it }, { dropChoosed = it })
    else if (showCamera)
        ImageSelectionScreen(
            { viewModel.onTakePhoto(it) },
            { showCamera = it }
        )
    else {
        screenView(
            viewModel,
            zipCode,
            { zipCode = it },
            street,
            { street = it },
            number,
            { number = it },
            district,
            { district = it },
            city,
            { city = it },
            state,
            { state = it },
            country,
            { country = it },
            showForm,
            { showForm = it },
            onShowSnackbar,
            onBackClick,
            title,
            { title = it },
            description,
            { description = it },
            dropChoosed = { dropChoosed = it }, listDrop, dropChoosed,
            { showCamera = it },
            { saveForm = it },
            saveForm
        )
    }
}

@Suppress("LongMethod")
@Composable
fun screenView(
    viewModel: NewServiceViewModel,
    zipCode: String,
    zipCodeChange: (String) -> Unit,
    street: String,
    streetChange: (String) -> Unit,
    number: String,
    numberChange: (String) -> Unit,
    district: String,
    districtChange: (String) -> Unit,
    city: String,
    cityChange: (String) -> Unit,
    state: String,
    stateChange: (String) -> Unit,
    country: String,
    countryChange: (String) -> Unit,
    showForm: Boolean,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    title: String,
    titleChange: (String) -> Unit,
    description: String,
    descriptionChange: (String) -> Unit,
    dropChoosed: (DropListUiModel) -> Unit,
    listCategoryDrop: List<DropListUiModel>,
    itemDropChoosed: DropListUiModel,
    showDialogChangePhotoChange: (Boolean) -> Unit,
    saveFormChange: (Boolean) -> Unit,
    saveForm: Boolean,
) {
    val context = LocalContext.current
    var messageErrorForm by rememberSaveable { mutableStateOf("") }
    if (messageErrorForm.isNotEmpty())
        LaunchedEffect(true) {
            onShowSnackbar(messageErrorForm, "")
            messageErrorForm = ""
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_grey_F7F7F9))
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            CircularIconButton(
                onClick = {
                    onBackClick()
                },
                icon = Icons.Rounded.ArrowBack,
                backgroundColor = colorResource(id = R.color.background_grey_F7F7F9),
                iconSize = 50.dp
            )
        }

        formBehaviour(
            showForm,
            zipCode,
            zipCodeChange,
            street,
            streetChange,
            number,
            numberChange,
            district,
            districtChange,
            city,
            cityChange,
            state,
            stateChange,
            country,
            countryChange,
            viewModel,
            onShowFormChange,
            onShowSnackbar,
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            title, titleChange,
            description, descriptionChange,
            dropChoosed, listCategoryDrop, itemDropChoosed,
            showDialogChangePhotoChange
        )
        var colorButton: Color
        var clickButton: () -> Unit
        var textColorButton: Color
        if (saveForm) {
            colorButton = colorResource(R.color.background_grey_F7F7F9)
            textColorButton = Color.Black
            clickButton = {}
        } else {
            colorButton = colorResource(R.color.colorPrimary)
            textColorButton = Color.White
            clickButton = {
                when (val result = validAddressForm(
                    zipCode,
                    street,
                    number,
                    title,
                    description,
                    itemDropChoosed,
                    viewModel.uri.size
                )) {
                    -1 -> {
                        saveFormChange(true)
                        viewModel.saveService(
                            NewServiceUserUiModel(
                                uuid_user_creator = null,
                                street = street,
                                number = number.toInt(),
                                district = district,
                                city = city,
                                state = state,
                                country = country,
                                name = title,
                                description = description,
                                image = listOf(),
                                zip_code = zipCode,
                                category_id = itemDropChoosed.id
                            )
                        )
                    }

                    else -> {
                        messageErrorForm =
                            context.getString(fill_correct_field) + " " + context.getString(result)
                    }
                }
            }
        }
        updateButton(
            clickButton, colorButton, Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            textColorButton
        )
    }
}

fun validAddressForm(
    zipCodeLabel: String,
    streetLabel: String,
    numberLabel: String,
    titleLabel: String,
    descriptionLabel: String,
    dropChoosed: DropListUiModel,
    listImagesSize: Int
): Int {
    return if (dropChoosed.id == -1) category
    else if (titleLabel.isEmpty()) title
    else if (descriptionLabel.isEmpty()) description
    else if (listImagesSize==0) photo
    else if (zipCodeLabel.isEmpty()) R.string.zip_code
    else if (streetLabel.isEmpty()) R.string.street
    else if (numberLabel.isEmpty()) R.string.number
    else -1
}

@Composable
fun updateButton(
    clickButton: () -> Unit,
    colorButton: Color,
    modifier: Modifier,
    textColorButton: Color,
) {
    ArrudeiaButtonColor(
        onClick = clickButton,
        modifier = modifier,
        colorButton = colorButton,
    ) {
        Text(
            text = "Salvar",
            style = MaterialTheme.typography.titleMedium,
            color = textColorButton,
        )
    }
}


@Composable
fun formBehaviour(
    showForm: Boolean,
    zipCode: String,
    zipCodeChange: (String) -> Unit,
    street: String,
    streetChange: (String) -> Unit,
    number: String,
    numberChange: (String) -> Unit,
    district: String,
    districtChange: (String) -> Unit,
    city: String,
    cityChange: (String) -> Unit,
    state: String,
    stateChange: (String) -> Unit,
    country: String,
    countryChange: (String) -> Unit,
    viewModel: NewServiceViewModel,
    onShowFormChange: (Boolean) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier,
    title: String,
    titleChange: (String) -> Unit,
    description: String,
    descriptionChange: (String) -> Unit,
    dropChoosed: (DropListUiModel) -> Unit,
    listCategoryDrop: List<DropListUiModel>,
    itemDropChoosed: DropListUiModel,
    showDialogChangePhotoChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            title(modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.size(6.dp))
        form(
            zipCode, zipCodeChange,
            street, streetChange,
            number, numberChange,
            district, districtChange,
            city, cityChange,
            state, stateChange,
            country, countryChange,
            title, titleChange,
            description, descriptionChange,
            dropChoosed, listCategoryDrop,
            itemDropChoosed,
            viewModel,
            showDialogChangePhotoChange
        )

    }
}


@Composable
private fun title(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            text = "Serviço",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Preencha o máximo de informações possíveis para facilitar a busca do seu serviço.",
            fontSize = 14.sp,
            color = colorResource(id = R.color.text_grey),
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Suppress("LongMethod")
@Composable
fun form(
    zipCode: String,
    zipCodeChange: (String) -> Unit,
    street: String,
    streetChange: (String) -> Unit,
    number: String,
    numberChange: (String) -> Unit,
    district: String,
    districtChange: (String) -> Unit,
    city: String,
    cityChange: (String) -> Unit,
    state: String,
    stateChange: (String) -> Unit,
    country: String,
    countryChange: (String) -> Unit,
    title: String,
    titleChange: (String) -> Unit,
    description: String,
    descriptionChange: (String) -> Unit,
    dropChoosed: (DropListUiModel) -> Unit,
    listCategoryDrop: List<DropListUiModel>,
    itemDropChoosed: DropListUiModel,
    viewModel: NewServiceViewModel,
    showDialogChangePhotoChange: (Boolean) -> Unit,
) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        DropDown(
            listCategoryDrop,
            { dropChoosed(it) },
            dropChoosed = itemDropChoosed,
            Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(14.dp))
        TextFieldInput(
            hint = "Digite um título para o serviço",
            title,
            icon = Icons.Rounded.Build,
            onValueChange = titleChange,
            KeyboardType.Number,
            ImeAction.Next
        )
        Spacer(modifier = Modifier.size(14.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            value = description,
            onValueChange = descriptionChange,
            label = { Text(text = "Descrição do serviço", color = Color.Black) },
            minLines = 2,
            maxLines = 6,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Build,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        )
        Spacer(modifier = Modifier.size(14.dp))
        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                colorResource(id = R.color.colorPrimary)
            ),
            onClick = {
                showDialogChangePhotoChange(true)
            },
        ) {
            Text(
                "Tirar foto",
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.size(4.dp))
        val listImages by remember { mutableStateOf(viewModel.uri) }
        LazyRow(modifier = Modifier.fillMaxHeight()) {
            items(items = listImages, itemContent = {
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(top = 20.dp)
                ) {

                    GlideImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,

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
                Spacer(modifier = Modifier.size(8.dp))
            })
        }

        Spacer(modifier = Modifier.size(4.dp))

        ArrudeiaddressForm(
            zipCode,
            zipCodeChange,
            street,
            streetChange,
            number,
            numberChange,
            district,
            districtChange,
            city,
            cityChange,
            state,
            stateChange,
            country,
            countryChange
        )
        Spacer(modifier = Modifier.size(80.dp))
    }
}

@Composable
private fun fetchData(
    viewModel: NewServiceViewModel,
    listDrop: (List<DropListUiModel>) -> Unit,
    dropChoosed: (DropListUiModel) -> Unit,
) {

    val uiStateExpertise by viewModel.sharedFlowExpertise.collectAsStateWithLifecycle()
    viewModel.fetchDataExpertise()
    when (uiStateExpertise) {
        is ServiceExpertiseUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
            )
        }

        is ServiceExpertiseUiState.Error -> {
            Text(
                text = stringResource((uiStateExpertise as ServiceExpertiseUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is ServiceExpertiseUiState.Success -> {
            val list = mutableListOf<DropListUiModel>(
                DropListUiModel(
                    stringResource(id = com.arrudeia.feature.services.R.string.category),
                    Icons.Outlined.Build,
                    -1
                )
            )
            list.addAll((uiStateExpertise as ServiceExpertiseUiState.Success).list.toListServiceExpertiseTranslated())
            listDrop(
                list
            )
            dropChoosed(list[0])
        }
    }
}
