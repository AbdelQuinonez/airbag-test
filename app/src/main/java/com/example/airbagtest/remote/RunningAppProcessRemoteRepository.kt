package com.example.airbagtest.remote

import com.example.airbagtest.database.model.RunningAppProcessCache

interface RunningAppProcessRemoteRepository {

    suspend fun insert(runningAppProcessCache: List<RunningAppProcessCache>):Boolean

}