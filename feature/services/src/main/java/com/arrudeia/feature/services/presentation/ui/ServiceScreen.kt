package com.arrudeia.feature.services.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.DropDown
import com.arrudeia.core.designsystem.component.DropListUiModel
import com.arrudeia.core.designsystem.component.TextSwitch
import com.arrudeia.core.profile.param.ProfilePersonalParam
import com.arrudeia.feature.services.R
import com.arrudeia.feature.services.R.string.my_services
import com.arrudeia.feature.services.R.string.request_a_service
import com.arrudeia.feature.services.R.string.service_oppotunities
import com.arrudeia.feature.services.presentation.model.ServiceUIModel
import com.arrudeia.feature.services.presentation.navigation.param.ChatParam
import com.arrudeia.feature.services.presentation.navigation.param.NewServiceParam
import com.arrudeia.feature.services.presentation.navigation.param.ServiceDetailParam
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist.Userlist
import com.arrudeia.feature.services.presentation.viewmodel.ServiceExpertiseUiState
import com.arrudeia.feature.services.presentation.viewmodel.ServiceUiState
import com.arrudeia.feature.services.presentation.viewmodel.ServiceViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
internal fun ServiceRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    serviceDetailNavigationClick: (ServiceDetailParam) -> Unit,
    onChatClick: (ChatParam) -> Unit,
    onNewServiceNavigationClick: (NewServiceParam) -> Unit,
    onProfilePersonalParamNavigationClick: (ProfilePersonalParam) -> Unit,
) {
    Pager(
        serviceDetailNavigationClick,
        onChatClick = onChatClick,
        onShowSnackbar = onShowSnackbar,
        onNewServiceNavigationClick = onNewServiceNavigationClick,
        onProfilePersonalParamNavigationClick = onProfilePersonalParamNavigationClick
    )
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun Pager(
    serviceDetailNavigationClick: (ServiceDetailParam) -> Unit,
    viewModel: ServiceViewModel = hiltViewModel(),
    onChatClick: (ChatParam) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNewServiceNavigationClick: (NewServiceParam) -> Unit,
    onProfilePersonalParamNavigationClick: (ProfilePersonalParam) -> Unit,
) {
    val pages = listOf(
        stringResource(id = service_oppotunities),
        stringResource(id = my_services),
    )
    var pagerState = rememberPagerState(initialPage = 0) { pages.size }
    var selectedTab by rememberSaveable { mutableIntStateOf(pagerState.currentPage) }
    var loadData by rememberSaveable { mutableStateOf(true) }
    var listDrop by rememberSaveable { mutableStateOf(listOf<DropListUiModel>()) }
    var listServices by rememberSaveable { mutableStateOf(listOf<ServiceUIModel>()) }
    var dropChoosed by remember {
        mutableStateOf<DropListUiModel?>(
            null
        )
    }

    val hasSavedDocId by remember { mutableStateOf(viewModel.hasIdentificationDoc) }
    val isLoading by remember { mutableStateOf(viewModel.isLoading) }
    var callingNewService by remember { mutableStateOf(false) }
    var openProfile by remember { mutableStateOf(false) }

    val docErrorMsg = stringResource(id = R.string.do_you_need_fill_all_personal_information)

    if (callingNewService) {
        callingNewService = false
        if (hasSavedDocId.value)
            onNewServiceNavigationClick(NewServiceParam())
        else {
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    onShowSnackbar(docErrorMsg, "")
                    delay(300)
                    openProfile = true
                }
            }
        }
    }

    if (openProfile) {
        openProfile = false
        onProfilePersonalParamNavigationClick(ProfilePersonalParam())
    }


    if (isLoading.value)
        ArrudeiaLoadingWheel(
            modifier = Modifier
        )

    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = background_grey_F7F7F9))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = background_grey_F7F7F9)),
            horizontalAlignment = CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(10.dp))

            TextSwitch(
                modifier = Modifier.padding(8.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .padding(8.dp),
                selectedIndex = selectedTab,
                items = pages,
                onSelectionChange = {
                    selectedTab = it
                }
            )

            Spacer(modifier = Modifier.size(10.dp))
            HorizontalPager(state = pagerState) { currentPage ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = CenterHorizontally
                ) {
                    when (currentPage) {
                        0 -> {


                            if (!loadData) {
                                if (listDrop.isNotEmpty() && listDrop[0].id != -1) {
                                    val dropComponentList = mutableListOf(
                                        DropListUiModel(
                                            stringResource(id = R.string.category),
                                            Icons.Outlined.Build,
                                            -1
                                        )
                                    )
                                    dropComponentList.addAll(listDrop.toListServiceExpertiseTranslated())
                                    listDrop = dropComponentList

                                }
                                if (dropChoosed == null && listDrop.isNotEmpty())
                                    dropChoosed = listDrop[0]
                                if (dropChoosed != null) {
                                    DropDown(
                                        listDrop,
                                        { dropChoosed = it },
                                        dropChoosed = dropChoosed!!
                                    )
                                    Service(
                                        services = listServices,
                                        onReceiptDetailClick = serviceDetailNavigationClick,
                                        viewModel = viewModel,
                                        dropChoosed = dropChoosed!!
                                    )
                                }

                            } else {
                                fetchData(
                                    viewModel,
                                    { listDrop = it },
                                    { listServices = it },
                                    { loadData = it })
                            }
                        }

                        1 -> {
                            Userlist(
                                onChatClick = onChatClick,
                                onShowSnackbar = onShowSnackbar
                            )
                        }
                    }
                }
            }
        }
        val titleAddNewPlace = stringResource(id = request_a_service)
        ExtendedFloatingActionButton(
            text = { Text(text = titleAddNewPlace) },
            icon = { Icon(Icons.Rounded.Add, null) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            shape = CircleShape,
            onClick = {
                callingNewService = true
                viewModel.checkHasIdentificationDoc()
            },
            containerColor = colorResource(id = colorPrimary),
            contentColor = Color.White
        )
    }
}

@Composable
fun List<DropListUiModel>.toListServiceExpertiseTranslated(): List<DropListUiModel> {
    val context = LocalContext.current
    return this.map {
        DropListUiModel(
            EnumServiceExpertise.getStringFromEnum(
                context,
                EnumServiceExpertise.getEnumValue(it.name)
            ),
            it.icon,
            it.id
        )
    }
}


@Composable
private fun fetchData(
    viewModel: ServiceViewModel,
    listDrop: (List<DropListUiModel>) -> Unit,
    listServices: (List<ServiceUIModel>) -> Unit,
    loadData: (Boolean) -> Unit,
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
            listDrop(
                (uiStateExpertise as ServiceExpertiseUiState.Success).list
            )
        }
    }

    val uiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData()
    when (uiState) {
        is ServiceUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
            )
        }

        is ServiceUiState.Error -> {
            Text(
                text = stringResource((uiState as ServiceUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is ServiceUiState.Success -> {
            listServices(
                (uiState as ServiceUiState.Success).list
            )
            loadData(false)
        }
    }
}

