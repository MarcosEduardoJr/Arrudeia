package com.arrudeia.feature.sign.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.feature.sign.R
import com.arrudeia.feature.sign.presentation.viewmodel.SignViewModel


private const val LINK_1 = "link_1"
private const val LINK_2 = "link_2"
private const val SPACING_FIX = 3f


@Composable
internal fun RegisterOnboardingScreen(
    onRouteClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: SignViewModel = hiltViewModel(),
) {

    val context = LocalContext.current


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
                painter = painterResource(id = R.drawable.ic_bg_onboarding),
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
                html = stringResource(id = R.string.register_description_important)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {

                var colorBtn = com.arrudeia.core.designsystem.R.color.colorPrimary
                var enableBtn = true
                ArrudeiaButtonColor(
                    onClick = {
                    },
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    colorButton = colorResource(colorBtn),
                ) {
                    Text(
                        text = stringResource(id = R.string.start),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

