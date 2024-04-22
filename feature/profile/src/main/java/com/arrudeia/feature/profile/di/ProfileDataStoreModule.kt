package com.arrudeia.feature.profile.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepository
import com.arrudeia.feature.profile.data.ProfileDataStoreUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileDataStoreUserRepositoryModule {

    @Provides
    @Singleton
    fun provideProfileDataStoreUserRepository(dataStore: DataStore<Preferences>): ProfileDataStoreUserRepository {
        return  ProfileDataStoreUserRepositoryImpl(dataStore)
    }
}

