package com.arrudeia.core.data.di

import com.arrudeia.core.data.repository.DefaultRecentSearchRepository
import com.arrudeia.core.data.repository.DefaultSearchContentsRepository
import com.arrudeia.core.data.repository.OfflineFirstUserDataRepository
import com.arrudeia.core.data.repository.RecentSearchRepository
import com.arrudeia.core.data.repository.SearchContentsRepository
import com.arrudeia.core.data.repository.UserDataRepository
import com.arrudeia.core.data.util.ConnectivityManagerNetworkMonitor
import com.arrudeia.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {


    @Binds
    fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: DefaultRecentSearchRepository,
    ): RecentSearchRepository

    @Binds
    fun bindsSearchContentsRepository(
        searchContentsRepository: DefaultSearchContentsRepository,
    ): SearchContentsRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
