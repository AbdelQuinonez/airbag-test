package com.example.airbagtest.di

import com.example.airbagtest.database.AppDatabase
import com.example.airbagtest.database.daos.RunningAppProcessDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideRunningAppProcessDao(appDatabase: AppDatabase): RunningAppProcessDao =
        appDatabase.runningAppProcess()

}