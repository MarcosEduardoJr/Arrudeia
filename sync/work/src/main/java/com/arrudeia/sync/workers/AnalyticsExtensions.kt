package com.arrudeia.sync.workers

import com.arrudeia.core.analytics.AnalyticsEvent
import com.arrudeia.core.analytics.AnalyticsHelper

fun AnalyticsHelper.logSyncStarted() =
    logEvent(
        AnalyticsEvent(type = "network_sync_started"),
    )

fun AnalyticsHelper.logSyncFinished(syncedSuccessfully: Boolean) {
    val eventType = if (syncedSuccessfully) "network_sync_successful" else "network_sync_failed"
    logEvent(
        AnalyticsEvent(type = eventType),
    )
}
