package com.example.airbagtest.remote

import com.example.airbagtest.remote.model.RunningAppProcessRemote

interface RunningAppProcessRemoteRepository {

    suspend fun insert(runningAppProcessRemote: List<RunningAppProcessRemote>):Boolean

}