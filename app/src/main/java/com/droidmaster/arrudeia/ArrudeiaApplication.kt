package com.droidmaster.arrudeia

import android.app.Application
import com.arrudeia.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class ArrudeiaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
    }


}
