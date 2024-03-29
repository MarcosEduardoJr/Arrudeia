package com.arrudeia.core.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesArrudeiaDatabase(
        @ApplicationContext context: Context,
    ): ArrudeiaDatabase = Room.databaseBuilder(
        context,
        ArrudeiaDatabase::class.java,
        "Arrudeia-database",
    ).build()
}
