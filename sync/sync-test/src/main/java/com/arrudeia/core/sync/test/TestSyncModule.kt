package com.arrudeia.core.sync.test

import com.arrudeia.core.data.util.SyncManager
import com.arrudeia.sync.di.SyncModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SyncModule::class],
)
interface TestSyncModule {
    @Binds
    fun bindsSyncStatusMonitor(
        syncStatusMonitor: NeverSyncingSyncManager,
    ): SyncManager
}
