package com.arrudeia.feature.sign.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arrudeia.feature.sign.data.SignDataStoreUserRepository
import com.arrudeia.feature.sign.data.SignDataStoreUserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SignDataStoreUserRepositoryModule {

    @Provides
    @Singleton
    fun provideSignDataStoreUserRepository(dataStore: DataStore<Preferences>): SignDataStoreUserRepository {
        return  SignDataStoreUserRepositoryImpl(dataStore)
    }
}

