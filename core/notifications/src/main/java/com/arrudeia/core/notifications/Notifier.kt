package com.arrudeia.core.notifications


/**
 * Interface for creating notifications in the app
 */
interface Notifier {
    fun postNewsNotifications(newsResources: List<Unit>)
}
