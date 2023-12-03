package com.arrudeia.core.notifications

import com.arrudeia.core.model.data.NewsResource

/**
 * Interface for creating notifications in the app
 */
interface Notifier {
    fun postNewsNotifications(newsResources: List<NewsResource>)
}
