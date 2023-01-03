package com.example.airbagtest.di

import com.example.airbagtest.remote.RunningAppProcessRemoteRepository
import com.example.airbagtest.remote.RunningAppProcessRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRemoteRepository {

    @Binds
    abstract fun bindRunningAppProcessRemoteRepository(
        runningAppProcessRemoteRepositoryImpl: RunningAppProcessRemoteRepositoryImpl
    ): RunningAppProcessRemoteRepository

}