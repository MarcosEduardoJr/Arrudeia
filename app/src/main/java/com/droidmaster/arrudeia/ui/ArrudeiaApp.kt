package com.droidmaster.arrudeia.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.component.ArrudeiaBackground
import com.arrudeia.core.designsystem.component.ArrudeiaGradientBackground
import com.arrudeia.core.designsystem.theme.GradientColors
import com.droidmaster.arrudeia.R
import com.droidmaster.arrudeia.navigation.ArrudeiaNavHost
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun ArrudeiaApp(
    windowSizeClass: WindowSizeClass,
    appState: ArrudeiaAppState = rememberArrudeiaAppState(
        windowSizeClass = windowSizeClass,
    ),
) {
    ArrudeiaBackground {
        ArrudeiaGradientBackground(
            gradientColors = GradientColors()
        ) {
            val snackbarHostState = remember { SnackbarHostState() }


            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = colorResource(id = background_grey_F7F7F9),
                contentColor = MaterialTheme.colorScheme.onBackground,
                snackbarHost = {
                    SnackbarHost(
                        snackbarHostState,
                    ) {
                        Snackbar(
                            snackbarData = it,
                            actionColor = Color.White,
                            contentColor = Color.White,
                            containerColor = Color.Black
                        )
                    }
                },
                bottomBar = {

                },
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {


                    Column(Modifier.fillMaxSize()) {
                        // Show the top app bar on top level destinations.


                        ArrudeiaNavHost(appState = appState, onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = Short,
                            ) == ActionPerformed
                        })
                    }
                }
            }
        }
    }
}

