package com.arrudeia.core.network.di

import com.arrudeia.core.network.NiaNetworkDataSource
import com.arrudeia.core.network.retrofit.RetrofitNiaNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredNetworkModule {

    @Binds
    fun RetrofitNiaNetwork.binds(): NiaNetworkDataSource
}
