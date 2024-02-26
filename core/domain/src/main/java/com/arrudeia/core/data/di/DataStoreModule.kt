package com.arrudeia.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.arrudeia.core.data.network.DataStoreUserRepositoryImpl
import com.arrudeia.core.data.repository.DataStoreUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val NAME_DATA_STORE = "user_data_store_arrudeia"



    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME_DATA_STORE)

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }


}


@Module
@InstallIn(SingletonComponent::class)
object DataStoreUserRepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreUserRepository(dataStore: DataStore<Preferences>): DataStoreUserRepository {
        return DataStoreUserRepositoryImpl(dataStore)
    }
}

