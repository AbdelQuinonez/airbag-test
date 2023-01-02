package com.example.airbagtest.core

import kotlinx.coroutines.flow.Flow

interface NetworkApi {

    fun connectivityStatus(): Flow<Status>

    enum class Status{
        Available, Unavailable, Lost, Losing
    }
}