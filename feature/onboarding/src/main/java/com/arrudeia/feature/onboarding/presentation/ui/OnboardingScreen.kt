package com.arrudeia.feature.onboarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.data.navigation.homeRoute
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.component.ArrudeiaButtonColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.designsystem.component.HtmlText
import com.arrudeia.core.location.util.getCurrentCityCountry
import com.arrudeia.core.utils.ShowPermissionLocationDialog
import com.arrudeia.feature.onboarding.R.drawable.ic_bg_onboarding
import com.arrudeia.feature.onboarding.R.string.onboarding_description_tired_job
import com.arrudeia.feature.onboarding.R.string.start
import com.arrudeia.feature.onboarding.presentation.viewmodel.OnboardingViewModel


@Composable
internal fun onboardingRoute(
    onRouteClick: (String) -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
    showBottomBar: (Boolean) -> Unit,
) {
    showBottomBar(false)
    val isFirstTimeOpenUseCase = viewModel.isFirstTimeOpenUseCase
    viewModel.getIsFirstTimeOpen()

    val currentUserSharedFlow by viewModel.currentUserSharedFlow.collectAsStateWithLifecycle()
    var loadedLocation by rememberSaveable {
        mutableStateOf(false)
    }
    CurrentLastLocation(callback = { city, country ->
        viewModel.updateLastLocation(city.orEmpty(), country.orEmpty())
        loadedLocation = true
    })

    if (!isFirstTimeOpenUseCase.value)
        onRouteClick(homeRoute)

    if (loadedLocation) {
        viewModel.loadLibKeys()
        when (currentUserSharedFlow) {
            is OnboardingViewModel.CurrentUserUiState.Success -> {
                onRouteClick(homeRoute)
            }

            is OnboardingViewModel.CurrentUserUiState.Error -> {
                onboarding(onRouteClick)
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ArrudeiaLoadingWheel(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(50.dp),
                    )
                }
            }
        }
    }
}


@Composable
fun CurrentLastLocation(
    callback: (String?, String?) -> Unit
) {
    val context = LocalContext.current
    var needPermission by rememberSaveable { mutableStateOf(true) }

    if (needPermission)
        ShowPermissionLocationDialog({ needPermission = it })

    if (needPermission.not()) {
        getCurrentCityCountry(context) { currentState, currentCountry ->
            callback(currentState, currentCountry)
        }
    }
}


@Composable
internal fun onboarding(
    onRouteClick: (String) -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
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
                onClick = {
                    viewModel.saveIsFirstTimeOpenUseCase()
                    onRouteClick(homeRoute)
                },
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


