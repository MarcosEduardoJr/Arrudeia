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
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.profileinstaller.ProfileVerifier
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.droidmaster.arrudeia.ui.arrudeiaApp
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        FirebaseFirestore.getInstance().firestoreSettings =
            FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

        enableEdgeToEdge()

        setContent {
            DisposableEffect(false) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        ContextCompat.getColor(applicationContext, R.color.background_grey_F7F7F9),
                        ContextCompat.getColor(applicationContext, R.color.background_grey_F7F7F9),
                    ) { false },
                    navigationBarStyle = SystemBarStyle.auto(
                        ContextCompat.getColor(applicationContext, R.color.background_grey_F7F7F9),
                        ContextCompat.getColor(applicationContext, R.color.background_grey_F7F7F9),
                    ) { false },
                )
                onDispose {}
            }

             ArrudeiaTheme(
                    darkTheme = false,
                    androidTheme = false,
                    disableDynamicTheming = false,
                ) {
                    arrudeiaApp(

                        windowSizeClass = calculateWindowSizeClass(this),
                    )
                }
        }
    }
}
