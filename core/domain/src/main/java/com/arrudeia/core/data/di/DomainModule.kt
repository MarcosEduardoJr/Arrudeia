package com.arrudeia.core.data.di

import com.arrudeia.core.data.network.DefaultHomeTravelsRepositoryImpl
import com.arrudeia.core.data.repository.DefaultHomeTravelsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun bindsRetrofitNetworkDataSource(
        networkMonitor: DefaultHomeTravelsRepositoryImpl,
    ): DefaultHomeTravelsRepository


}
