package com.arrudeia.core.run.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arrudeia.core.run.data.dao.RunDao
import com.arrudeia.core.run.data.entity.RunEntity

@Database(
    entities = [
        RunEntity::class
    ],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
}