package com.example.airbagtest.di

import com.example.airbagtest.utils.AppDispatcher
import com.example.airbagtest.utils.AppDispatcherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsAppDispatcher {

    @Binds
    abstract fun provideAppDispatcher(appDispatcherImpl: AppDispatcherImpl): AppDispatcher
}