package com.arrudeia.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arrudeia.core.database.dao.NewsResourceDao
import com.arrudeia.core.database.dao.NewsResourceFtsDao
import com.arrudeia.core.database.dao.RecentSearchQueryDao
import com.arrudeia.core.database.dao.TopicDao
import com.arrudeia.core.database.dao.TopicFtsDao
import com.arrudeia.core.database.model.NewsResourceEntity
import com.arrudeia.core.database.model.NewsResourceFtsEntity
import com.arrudeia.core.database.model.NewsResourceTopicCrossRef
import com.arrudeia.core.database.model.RecentSearchQueryEntity
import com.arrudeia.core.database.model.TopicEntity
import com.arrudeia.core.database.model.TopicFtsEntity
import com.arrudeia.core.database.util.InstantConverter

@Database(
    entities = [
        NewsResourceEntity::class,
        NewsResourceTopicCrossRef::class,
        NewsResourceFtsEntity::class,
        TopicEntity::class,
        TopicFtsEntity::class,
        RecentSearchQueryEntity::class,
    ],
    version = 14,
    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
)
abstract class ArrudeiaDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun newsResourceDao(): NewsResourceDao
    abstract fun topicFtsDao(): TopicFtsDao
    abstract fun newsResourceFtsDao(): NewsResourceFtsDao
    abstract fun recentSearchQueryDao(): RecentSearchQueryDao
}
