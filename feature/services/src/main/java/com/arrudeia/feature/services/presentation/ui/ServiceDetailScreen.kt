package com.arrudeia.feature.services.presentation.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.core.profile.param.ProfilePersonalParam
import com.arrudeia.feature.services.R
import com.arrudeia.feature.services.presentation.model.ServiceDetailUiModel
import com.arrudeia.feature.services.presentation.navigation.param.NewServiceParam
import com.arrudeia.feature.services.presentation.navigation.param.ServiceDetailParam
import com.arrudeia.feature.services.presentation.ui.chat.chatwithme.presentation.userlist.UserListViewModel
import com.arrudeia.feature.services.presentation.viewmodel.ServiceDetailUiState
import com.arrudeia.feature.services.presentation.viewmodel.ServiceDetailViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun ServiceDetailRoute(
    viewModel: ServiceDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    args: ServiceDetailParam,
    onProfilePersonalParamNavigationClick: (ProfilePersonalParam) -> Unit,
) {


    val hasSavedDocId by remember { mutableStateOf(viewModel.hasIdentificationDoc) }
    val isLoading by remember { mutableStateOf(viewModel.isLoading) }
    var callingNewService by remember { mutableStateOf(false) }
    var openProfile by remember { mutableStateOf(false) }
    var uuidUserCreator by remember { mutableStateOf("") }

    val docErrorMsg = stringResource(id = R.string.do_you_need_fill_all_personal_information_try_do_job)

    if (callingNewService) {
        callingNewService = false
        if (hasSavedDocId.value) {
            viewModel.createFriendshipRegisterToFirebase(
                uuidUserCreator
            )
        } else {
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

    val arrTvUiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData(args.id)
    when (arrTvUiState) {
        is ServiceDetailUiState.Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is ServiceDetailUiState.Error -> {
            Text(
                text = stringResource((arrTvUiState as ServiceDetailUiState.Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is ServiceDetailUiState.Success -> {
            val item = (arrTvUiState as ServiceDetailUiState.Success).item
            receiptDetail(
                item,
                onBackClick,
                { callingNewService = it },
                { uuidUserCreator = it }
            )
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun receiptDetail(
    item: ServiceDetailUiModel?,
    onBackClick: () -> Unit,
    callingNewService: (Boolean) -> Unit,
    uuidUserCreator: (String) -> Unit,
    viewModel: UserListViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    ArrudeiaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorResource(id = background_grey_F7F7F9)
            ) {
                Box(
                    modifier = with(Modifier) {
                        fillMaxSize()
                    })
                {
                    LazyRow(modifier = Modifier.fillMaxHeight()) {
                        itemsIndexed(items = item?.image.orEmpty(), itemContent = { index, it ->
                            Box(
                                modifier = with(Modifier) {
                                    width(screenWidth)
                                    height(300.dp)
                                })
                            {
                                GlideImage(
                                    model = it?.url.orEmpty(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(screenWidth)
                                        .height(300.dp),
                                    contentScale = ContentScale.Crop,
                                )
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 60.dp),
                                    text = "${index + 1}/${item?.image?.size}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = colorResource(id = colorPrimary)
                                )
                            }
                        })
                    }
                    ServiceDetailContent(item = item)
                    ArrudeiaButtonColor(
                        onClick = {
                            callingNewService(true)
                            uuidUserCreator(item?.uuidUserCreator.orEmpty())
                            viewModel.checkHasIdentificationDoc()

                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colorButton = colorResource(colorPrimary),
                    ) {
                        Text(
                            text = stringResource(id = R.string.get_in_touch),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                        )
                    }
                }
            }
            buttonBottom(
                onBackClick, Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

/**
 * Composable function that displays an ExoPlayer to play a video using Jetpack Compose.
 *
 * @OptIn annotation to UnstableApi is used to indicate that the API is still experimental and may
 * undergo changes in the future.
 *
 */
@OptIn(UnstableApi::class)
@Composable
fun ExoPlayerView() {
    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context).build()

    // Create a MediaSource
    val mediaSource =
        remember("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") {
            MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set your desired height
    )
}

@Composable
private fun buttonBottom(onBackClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {

        CircularIconButton(
            onClick = {
                onBackClick()
            },
            icon = Icons.Rounded.ArrowBack,
            backgroundColor = colorResource(id = background_grey_F7F7F9),
            iconSize = 50.dp,
            modifier = Modifier
        )
    }
}
