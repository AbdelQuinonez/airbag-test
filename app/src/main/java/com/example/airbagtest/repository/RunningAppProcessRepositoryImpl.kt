package com.example.airbagtest.repository

import com.example.airbagtest.database.daos.RunningAppProcessDao
import com.example.airbagtest.database.model.RunningAppProcessCache
import com.example.airbagtest.model.RunningAppProcess
import javax.inject.Inject

class RunningAppProcessRepositoryImpl @Inject constructor(
    private val runningAppProcessDao: RunningAppProcessDao,
) : RunningAppProcessRepository {

    override suspend fun insertRunningAppProcesses(appRunningList: List<RunningAppProcessCache>): List<Long> =
        runningAppProcessDao.insertAll(appRunningList)


    override suspend fun getRunningAppProcesses(): List<RunningAppProcessCache> =
        runningAppProcessDao.getAll()

}