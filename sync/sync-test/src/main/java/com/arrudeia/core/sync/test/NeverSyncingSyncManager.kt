package com.arrudeia.core.sync.test

import com.arrudeia.core.data.util.SyncManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class NeverSyncingSyncManager @Inject constructor() : SyncManager {
    override val isSyncing: Flow<Boolean> = flowOf(false)
    override fun requestSync() = Unit
}
