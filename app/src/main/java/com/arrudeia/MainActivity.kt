package com.arrudeia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.multidex.MultiDex
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.AndroidEntryPoint
import com.arrudeia.core.designsystem.R.color.background_grey_F7F7F9
import com.arrudeia.core.designsystem.theme.ArrudeiaTheme
import com.arrudeia.MainActivityViewModel
import com.arrudeia.ui.arrudeiaApp
import com.example.chatwithme.domain.model.UserStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        FirebaseApp.initializeApp(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        FirebaseFirestore.getInstance().firestoreSettings =
            FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()




        enableEdgeToEdge()

        setContent {
            mainViewModel = hiltViewModel()
            mainViewModel.setUserStatusToFirebase(UserStatus.ONLINE)
            DisposableEffect(false) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        ContextCompat.getColor(applicationContext, background_grey_F7F7F9),
                        ContextCompat.getColor(applicationContext, background_grey_F7F7F9),
                    ) { false },
                    navigationBarStyle = SystemBarStyle.auto(
                        ContextCompat.getColor(applicationContext, background_grey_F7F7F9),
                        ContextCompat.getColor(applicationContext, background_grey_F7F7F9),
                    ) { false },
                )
                onDispose {}
            }

            ArrudeiaTheme(
            ) {
                arrudeiaApp(

                    windowSizeClass = calculateWindowSizeClass(this),
                )
            }
        }
    }


    override fun onResume() {
        if (this::mainViewModel.isInitialized)
            mainViewModel.setUserStatusToFirebase(UserStatus.ONLINE)
        super.onResume()
    }

    override fun onPause() {
        if (this::mainViewModel.isInitialized)
            mainViewModel.setUserStatusToFirebase(UserStatus.OFFLINE)

        super.onPause()
    }


    override fun onDestroy() {
        if (this::mainViewModel.isInitialized)
            mainViewModel.setUserStatusToFirebase(UserStatus.OFFLINE)
        super.onDestroy()
    }
}
