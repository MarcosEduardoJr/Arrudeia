package com.arrudeia.feature.stories

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.NiaButtonColor
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.stories.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.stories.R.string.tools_title_trip_detail_name
import com.arrudeia.feature.stories.R.string.tools_currency_price
import com.arrudeia.feature.stories.R.string.from
import com.arrudeia.feature.stories.R.string.tools_subtitle_trip_detail_name
import com.arrudeia.feature.stories.R.string.description
import com.arrudeia.feature.stories.R.string.tools_lorem_ipsum

import com.arrudeia.feature.stories.R.string.talk_with_agency
import com.arrudeia.navigation.homeRoute


private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
fun StoriesRoute(
    onStoriesId: (String) -> Unit,
) {
    Stories(
        onStoriesId
    )
}

@SuppressLint("DesignSystem")
@Composable
internal fun Stories(onStoriesId: (String) -> Unit) {
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
                Text(text = "aew caeraoiiiiiiiooo")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewAux() {
    Stories {}
}

