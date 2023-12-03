package com.arrudeia.core.testing.util

import com.arrudeia.core.analytics.AnalyticsEvent
import com.arrudeia.core.analytics.AnalyticsHelper

class TestAnalyticsHelper : AnalyticsHelper {

    private val events = mutableListOf<AnalyticsEvent>()
    override fun logEvent(event: AnalyticsEvent) {
        events.add(event)
    }

    fun hasLogged(event: AnalyticsEvent) = events.contains(event)
}
