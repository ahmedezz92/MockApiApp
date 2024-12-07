package com.example.mockapiapplication.data.core.data.module

import android.content.Context
import com.example.mockapiapplication.data.datasource.local.AppDao
import com.example.mockapiapplication.data.datasource.local.AppDatabase
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
    fun provideWeatherDao(database: AppDatabase): AppDao {
        return database.appDao()
    }
}