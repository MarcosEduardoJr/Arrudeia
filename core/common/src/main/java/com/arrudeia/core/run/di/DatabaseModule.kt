package com.arrudeia.core.run.di

import android.content.Context
import androidx.room.Room
import com.arrudeia.core.run.data.RunDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRunDatabase(
        @ApplicationContext context: Context,
    ): RunDatabase = Room.databaseBuilder(
        context,
        RunDatabase::class.java,
        "run_database",
    ).build()

}

