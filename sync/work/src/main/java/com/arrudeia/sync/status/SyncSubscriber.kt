package com.arrudeia.sync.status

/**
 * Subscribes to backend requested synchronization
 */
interface SyncSubscriber {
    suspend fun subscribe()
}
