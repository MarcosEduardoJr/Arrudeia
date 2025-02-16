package com.arrudeia.core.designsystem.component

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.arrudeia.core.designsystem.R
import kotlin.math.max

private const val SPACING_FIX = 3f

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    normalColor: Int = R.color.colorWhite,
    highlightColor: Int = R.color.colorPrimary,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    alignmentText: Int = View.TEXT_ALIGNMENT_CENTER
) {
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

                textAlignment = alignmentText
                textSize = textStyle.fontSize.value
                setLineSpacing(extraSpacing, 1f)
                val colorText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(normalColor, null)
                } else {
                    resources.getColorStateList(normalColor)
                }
                setTextColor(
                    colorText
                )
                setGravity(gravity)
                val colorTextHighLight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    resources.getColorStateList(highlightColor, null)
                } else {
                    resources.getColorStateList(highlightColor)
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