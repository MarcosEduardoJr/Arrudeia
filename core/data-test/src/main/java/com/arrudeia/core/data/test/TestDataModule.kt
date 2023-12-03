package com.arrudeia.core.data.test

import com.arrudeia.core.data.di.DataModule
import com.arrudeia.core.data.repository.RecentSearchRepository
import com.arrudeia.core.data.repository.SearchContentsRepository
import com.arrudeia.core.data.repository.UserDataRepository
import com.arrudeia.core.data.repository.fake.FakeRecentSearchRepository
import com.arrudeia.core.data.repository.fake.FakeSearchContentsRepository
import com.arrudeia.core.data.repository.fake.FakeUserDataRepository
import com.arrudeia.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class],
)
interface TestDataModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: FakeUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsRecentSearchRepository(
        recentSearchRepository: FakeRecentSearchRepository,
    ): RecentSearchRepository

    @Binds
    fun bindsSearchContentsRepository(
        searchContentsRepository: FakeSearchContentsRepository,
    ): SearchContentsRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: AlwaysOnlineNetworkMonitor,
    ): NetworkMonitor
}
