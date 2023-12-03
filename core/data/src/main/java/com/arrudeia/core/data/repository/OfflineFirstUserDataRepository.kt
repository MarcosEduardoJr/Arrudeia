package com.arrudeia.core.data.repository

import com.arrudeia.core.analytics.AnalyticsHelper
import com.arrudeia.core.datastore.ArrudeiaPreferencesDataSource
import com.arrudeia.core.model.data.ThemeBrand
import com.arrudeia.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: ArrudeiaPreferencesDataSource,
    private val analyticsHelper: AnalyticsHelper,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        niaPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        niaPreferencesDataSource.setThemeBrand(themeBrand)
        analyticsHelper.logThemeChanged(themeBrand.name)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
        analyticsHelper.logDynamicColorPreferenceChanged(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
        analyticsHelper.logOnboardingStateChanged(shouldHideOnboarding)
    }
}
