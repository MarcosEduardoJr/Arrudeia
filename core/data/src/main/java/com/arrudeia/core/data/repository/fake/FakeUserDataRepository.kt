package com.arrudeia.core.data.repository.fake

import com.arrudeia.core.data.repository.UserDataRepository
import com.arrudeia.core.datastore.ArrudeiaPreferencesDataSource
import com.arrudeia.core.model.data.ThemeBrand
import com.arrudeia.core.model.data.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fake implementation of the [UserDataRepository] that returns hardcoded user data.
 *
 * This allows us to run the app with fake data, without needing an internet connection or working
 * backend.
 */
class FakeUserDataRepository @Inject constructor(
    private val niaPreferencesDataSource: ArrudeiaPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        niaPreferencesDataSource.userData

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        niaPreferencesDataSource.setThemeBrand(themeBrand)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        niaPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        niaPreferencesDataSource.setShouldHideOnboarding(shouldHideOnboarding)
    }
}
