package com.takanakonbu.hobbytime.di

import android.content.Context
import com.takanakonbu.hobbytime.data.AppDatabase
import com.takanakonbu.hobbytime.data.dao.HobbyDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideHobbyDao(database: AppDatabase): HobbyDao {
        return database.hobbyDao()
    }
}