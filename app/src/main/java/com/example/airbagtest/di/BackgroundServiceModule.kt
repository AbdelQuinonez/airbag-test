package com.example.airbagtest.di

import android.content.Context
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BackgroundServiceModule {

    @Provides
    @Singleton
    fun provideWorkerParameters(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)
}