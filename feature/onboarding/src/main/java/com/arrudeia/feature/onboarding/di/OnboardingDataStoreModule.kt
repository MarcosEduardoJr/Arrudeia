package com.arrudeia.feature.onboarding.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepository
import com.arrudeia.feature.onboarding.data.OnboardingDataStoreUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OnboardingDataStoreModule {

    @Provides
    @Singleton
    fun provideOnboardingDataStoreUserRepository(dataStore: DataStore<Preferences>): OnboardingDataStoreUserRepository {
        return OnboardingDataStoreUserRepositoryImpl(dataStore)
    }
}

