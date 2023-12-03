package com.arrudeia.sync.di

import com.arrudeia.core.data.util.SyncManager
import com.arrudeia.sync.status.StubSyncSubscriber
import com.arrudeia.sync.status.SyncSubscriber
import com.arrudeia.sync.status.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {
    @Binds
    fun bindsSyncStatusMonitor(
        syncStatusMonitor: WorkManagerSyncManager,
    ): SyncManager

    @Binds
    fun bindsSyncSubscriber(
        syncSubscriber: StubSyncSubscriber,
    ): SyncSubscriber
}
