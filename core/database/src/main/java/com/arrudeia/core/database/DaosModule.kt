package com.arrudeia.core.database

import com.arrudeia.core.database.dao.NewsResourceDao
import com.arrudeia.core.database.dao.NewsResourceFtsDao
import com.arrudeia.core.database.dao.RecentSearchQueryDao
import com.arrudeia.core.database.dao.TopicDao
import com.arrudeia.core.database.dao.TopicFtsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: ArrudeiaDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesNewsResourceDao(
        database: ArrudeiaDatabase,
    ): NewsResourceDao = database.newsResourceDao()

    @Provides
    fun providesTopicFtsDao(
        database: ArrudeiaDatabase,
    ): TopicFtsDao = database.topicFtsDao()

    @Provides
    fun providesNewsResourceFtsDao(
        database: ArrudeiaDatabase,
    ): NewsResourceFtsDao = database.newsResourceFtsDao()

    @Provides
    fun providesRecentSearchQueryDao(
        database: ArrudeiaDatabase,
    ): RecentSearchQueryDao = database.recentSearchQueryDao()
}
