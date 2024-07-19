package com.arrudeia.feature.receipt.presentation.ui

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView

import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.CircularIconButton
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.receipt.presentation.model.ReceiptDetailUiModel
import com.arrudeia.feature.receipt.presentation.navigation.param.ReceiptDetailParam
import com.arrudeia.feature.receipt.presentation.viewmodel.ReceiptDetailViewModel
import com.arrudeia.feature.receipt.presentation.viewmodel.ReceiptsDetailUiState.Error
import com.arrudeia.feature.receipt.presentation.viewmodel.ReceiptsDetailUiState.Loading
import com.arrudeia.feature.receipt.presentation.viewmodel.ReceiptsDetailUiState.Success


import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
internal fun ReceiptDetailRoute(
    viewModel: ReceiptDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    args: ReceiptDetailParam,
) {
    val arrTvUiState by viewModel.sharedFlow.collectAsStateWithLifecycle()
    viewModel.fetchData(args.idReceipt)
    when (arrTvUiState) {
        is Loading -> {
            ArrudeiaLoadingWheel(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            )
        }

        is Error -> {
            Text(
                text = stringResource((arrTvUiState as Error).message),
                modifier = Modifier.padding(4.dp)
            )
        }

        is Success -> {
            val item = (arrTvUiState as Success).item
            receiptDetail(
                item,
                onBackClick,
                viewModel
            )
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("DesignSystem")
@Composable
internal fun receiptDetail(
    item: ReceiptDetailUiModel?,
    onBackClick: () -> Unit,
    viewModel: ReceiptDetailViewModel
) {
    val context = LocalContext.current

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

                    AndroidView(factory = {
                        var view = YouTubePlayerView(it)
                        val fragment = view.addYouTubePlayerListener(
                            object : AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    super.onReady(youTubePlayer)
                                    youTubePlayer.cueVideo(item?.urlVideo.orEmpty(), 0f)
                                }
                            }
                        )
                        view

                    })

                    ReceiptDetailContent(item = item)
                }
            }
            buttonBottom(
                onBackClick, Modifier.Companion
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
    val mediaSource = remember("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") {
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
