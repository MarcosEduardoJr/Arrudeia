package com.arrudeia.feature.sign

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.NiaButtonColor
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.feature.onboarding.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.onboarding.R.string.onboarding_description_tired_job
import com.arrudeia.feature.onboarding.R.string.start
import com.arrudeia.navigation.signRoute

@Composable
internal fun OnboardingRoute(
    onTopicClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    Onboarding(
        onTopicClick
    )
}


@Composable
internal fun Onboarding(onTopicClick: (String) -> Unit,) {
    ArrudeiaTheme {
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
                Text(
                    text = stringResource(id = onboarding_description_tired_job),
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )

                NiaButtonColor(
                    onClick = { onTopicClick(signRoute) },
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
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding {}
}
