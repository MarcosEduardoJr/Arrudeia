
package com.arrudeia.core.run.di

import com.arrudeia.core.run.data.RunDatabase
import com.arrudeia.core.run.data.dao.RunDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesRunDao(
        database: RunDatabase,
    ): RunDao = database.runDao

}
