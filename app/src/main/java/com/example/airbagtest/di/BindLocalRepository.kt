package com.example.airbagtest.di

import com.example.airbagtest.repository.RunningAppProcessRepository
import com.example.airbagtest.repository.RunningAppProcessRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindLocalRepository {

    @Binds
    abstract fun bindRunningAppProcessRepository(
        runningAppProcessRepositoryImpl: RunningAppProcessRepositoryImpl
    ): RunningAppProcessRepository

}