package com.arrudeia.feature.onboarding.presentation.ui

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.HtmlText
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.data.navigation.signRoute
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.feature.onboarding.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.onboarding.R.string.start
import com.arrudeia.feature.onboarding.R.string.onboarding_description_tired_job
import com.arrudeia.feature.onboarding.presentation.viewmodel.OnboardingViewModel
import com.arrudeia.feature.onboarding.presentation.viewmodel.OnboardingViewModel.CurrentUserUiState
import androidx.core.text.HtmlCompat
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.DefaultLinkMovementMethod
import kotlin.math.max

private const val SPACING_FIX = 3f


@Composable
internal fun onboardingRoute(
    onRouteClick: (String) -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
    showBottomBar: (Boolean) -> Unit,
) {
    showBottomBar(false)
    val currentUserSharedFlow by viewModel.currentUserSharedFlow.collectAsStateWithLifecycle()

    when (currentUserSharedFlow) {
        is CurrentUserUiState.Success -> {
            onRouteClick(homeRoute)
        }

        is CurrentUserUiState.Error -> {
            onboarding(onRouteClick)
        }

        else -> {}
    }
    viewModel.getCurrentUser()
}


@Composable
internal fun onboarding(onRouteClick: (String) -> Unit) {
     Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = {},
            bottomBar = {},
        ) { padding ->
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = ic_bg_onboarding),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds(),
                    contentScale = ContentScale.Crop,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
            ) {
                HtmlText(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 80.dp),
                    html = stringResource(id = onboarding_description_tired_job)
                )
                ArrudeiaButtonColor(
                    onClick = { onRouteClick(signRoute) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    colorButton = colorResource(colorPrimary),
                ) {
                    Text(
                        text = stringResource(id = start),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    onLink1Clicked: (() -> Unit)? = null,
    onLink2Clicked: (() -> Unit)? = null,
) {
    var textStyle = MaterialTheme.typography.headlineMedium
    AndroidView(
        modifier = modifier,
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY) },
        factory = { context ->
            val spacingReady =
                max(textStyle.lineHeight.value - textStyle.fontSize.value - SPACING_FIX, 0f)
            val extraSpacing = spToPx(spacingReady.toInt(), context)
            val gravity = when (textStyle.textAlign) {
                TextAlign.Center -> Gravity.CENTER
                TextAlign.End -> Gravity.END
                else -> Gravity.START
            }

            TextView(context).apply {

                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = textStyle.fontSize.value
                setLineSpacing(extraSpacing, 1f)
                val colorText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(R.color.colorWhite, null)
                } else {
                    resources.getColorStateList(R.color.colorWhite)
                }
                setTextColor(
                    colorText
                )
                setGravity(gravity)
                val colorTextHighLight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(colorPrimary, null)
                } else {
                    resources.getColorStateList(colorPrimary)
                }
                setLinkTextColor(colorTextHighLight)
                movementMethod = DefaultLinkMovementMethod(object :
                    DefaultLinkMovementMethod.OnLinkClickedListener {
                    override fun onLinkClicked(url: String?): Boolean {
                        return true
                    }
                })
            }
        }
    )
}

fun spToPx(sp: Int, context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        context.resources.displayMetrics
    )

