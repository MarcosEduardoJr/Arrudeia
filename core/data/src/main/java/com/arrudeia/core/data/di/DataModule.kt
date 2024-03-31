package com.arrudeia.core.data.di

import com.apollographql.apollo3.ApolloClient
import com.arrudeia.core.data.repository.ProfileRepository
import com.arrudeia.core.data.util.ConnectivityManagerNetworkMonitor
import com.arrudeia.core.data.util.NetworkMonitor
import com.arrudeia.core.network.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private const val ARRUDEIA_BASE_URL = BuildConfig.BACKEND_URL

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {


    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor


}
