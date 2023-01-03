package com.example.airbagtest.di

import com.example.airbagtest.core.NetworkApi
import com.example.airbagtest.core.NetworkApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindNetworkApi {

    @Binds
    abstract fun bindNetworkApi(
        networkApiImpl: NetworkApiImpl
    ): NetworkApi

}