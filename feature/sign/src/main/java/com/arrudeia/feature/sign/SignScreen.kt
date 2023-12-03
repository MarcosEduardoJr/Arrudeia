package com.arrudeia.feature.sign

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.component.DefaultLinkMovementMethod
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme

import com.arrudeia.feature.sign.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.sign.R.string.sign_description_tired_job
import com.arrudeia.feature.sign.R.string.title_sign
import com.arrudeia.feature.sign.R.string.please_sign_in_to_continue_our_app

import com.arrudeia.core.designsystem.R.color.colorWhite
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.NiaButtonColor
import com.arrudeia.navigation.homeRoute
import kotlin.math.max

private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
internal fun SignRoute(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: SignViewModel = hiltViewModel(),
) {
    Sign(
onRouteClick
    )
}


@SuppressLint("DesignSystem")
@Composable
internal fun Sign(onRouteClick: (String) -> Unit,) {
    ArrudeiaTheme {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            //body

            Surface(
                modifier = Modifier.fillMaxSize()
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
                    .background(Color.Black.copy(alpha = 0.7f)),
            ) {

                HtmlText(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 80.dp),
                    html = stringResource(id = sign_description_tired_job)
                )

                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    NiaButtonColor(
                        onClick = { onRouteClick(homeRoute) },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colorButton = colorResource(colorPrimary),
                    ) {
                        Text(
                            text = stringResource(id = title_sign),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                        text = stringResource(please_sign_in_to_continue_our_app),
                        color = Color.White.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.size(50.dp))
                }

            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnboardingPreview() {
    Sign{}
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
            /*val fontResId = when (textStyle.fontWeight) {
                FontWeight.Medium -> R.font.inter_medium
                else -> R.font.inter_regular
            }*/
            //  val font = ResourcesCompat.getFont(context, fontResId)

            TextView(context).apply {
                // general style
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                textSize = textStyle.fontSize.value
                setLineSpacing(extraSpacing, 1f)
                val colorText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(colorWhite, null)
                } else {
                    resources.getColorStateList(colorWhite)
                }
                setTextColor(
                    colorText
                )
                setGravity(gravity)
                //typeface = font
                // links
                val colorTextHighLight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(colorPrimary, null)
                } else {
                    resources.getColorStateList(colorPrimary)
                }
                setLinkTextColor(colorTextHighLight)
                movementMethod = DefaultLinkMovementMethod(object :
                    DefaultLinkMovementMethod.OnLinkClickedListener {
                    override fun onLinkClicked(url: String?): Boolean {
                        when (url) {
                            LINK_1 -> onLink1Clicked?.invoke()
                            LINK_2 -> onLink2Clicked?.invoke()
                        }
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
