package com.example.airbagtest.repository

import com.example.airbagtest.database.model.RunningAppProcessCache

interface RunningAppProcessRepository {

    suspend fun insertRunningAppProcesses(appRunningList :List<RunningAppProcessCache>): List<Long>

    suspend fun getRunningAppProcesses(): List<RunningAppProcessCache>

}