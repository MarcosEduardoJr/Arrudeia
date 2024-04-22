package com.droidmaster.arrudeia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.profileinstaller.ProfileVerifier
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.droidmaster.arrudeia.ui.ArrudeiaApp
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "MainActivity"

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Lazily inject [JankStats], which is used to track jank throughout the app.
     */
    @Inject
    lateinit var lazyStats: dagger.Lazy<JankStats>



    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        FirebaseFirestore.getInstance().firestoreSettings =
            FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()


        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(false) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        ContextCompat.getColor(applicationContext, R.color.background_grey_F7F7F9),
                        ContextCompat.getColor(applicationContext, R.color.background_grey_F7F7F9),
                    ) { false },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        lightScrim
                    ) { false },
                )
                onDispose {}
            }

             ArrudeiaTheme(
                    darkTheme = false,
                    androidTheme = false,
                    disableDynamicTheming = false,
                ) {
                    ArrudeiaApp(

                        windowSizeClass = calculateWindowSizeClass(this),
                    )
                }

        }
    }

    override fun onResume() {
        super.onResume()
        lazyStats.get().isTrackingEnabled = true
        lifecycleScope.launch {
            logCompilationStatus()
        }
    }

    override fun onPause() {
        super.onPause()
        lazyStats.get().isTrackingEnabled = false
    }

    /**
     * Logs the app's Baseline Profile Compilation Status using [ProfileVerifier].
     */
    private suspend fun logCompilationStatus() {
        /*
        When delivering through Google Play, the baseline profile is compiled during installation.
        In this case you will see the correct state logged without any further action necessary.
        To verify baseline profile installation locally, you need to manually trigger baseline
        profile installation.
        For immediate compilation, call:
         `adb shell cmd package compile -f -m speed-profile com.example.macrobenchmark.target`
        You can also trigger background optimizations:
         `adb shell pm bg-dexopt-job`
        Both jobs run asynchronously and might take some time complete.
        To see quick turnaround of the ProfileVerifier, we recommend using `speed-profile`.
        If you don't do either of these steps, you might only see the profile status reported as
        "enqueued for compilation" when running the sample locally.
        */
        withContext(Dispatchers.IO) {
            val status = ProfileVerifier.getCompilationStatusAsync().await()
            Log.d(TAG, "ProfileInstaller status code: ${status.profileInstallResultCode}")
            Log.d(
                TAG,
                when {
                    status.isCompiledWithProfile -> "ProfileInstaller: is compiled with profile"
                    status.hasProfileEnqueuedForCompilation() ->
                        "ProfileInstaller: Enqueued for compilation"

                    else -> "Profile not compiled or enqueued"
                },
            )
        }
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0x00, 0x00, 0x00)