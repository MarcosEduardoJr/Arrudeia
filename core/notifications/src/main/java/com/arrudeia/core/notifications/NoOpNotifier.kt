package com.arrudeia.core.notifications

import javax.inject.Inject

/**
 * Implementation of [Notifier] which does nothing. Useful for tests and previews.
 */
class NoOpNotifier @Inject constructor() : Notifier {
    override fun postNewsNotifications(newsResources: List<Unit>) = Unit
}
