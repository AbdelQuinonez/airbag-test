package com.example.airbagtest.interactors

import com.example.airbagtest.database.model.RunningAppProcessCache
import com.example.airbagtest.model.RunningAppProcess
import com.example.airbagtest.repository.RunningAppProcessRepository
import javax.inject.Inject


class GetBackgroundProcessDataBaseUseCase @Inject constructor(
    private val runningAppProcessRepository: RunningAppProcessRepository
) {

    suspend operator fun invoke(): List<RunningAppProcess>{
        val cacheList = getCacheList()
        return List(cacheList.size){ i ->
            val cacheProcess = cacheList[i]
            RunningAppProcess(
                processName = cacheProcess.processName,
            )
        }
    }

    suspend fun getCacheList(): List<RunningAppProcessCache> =
        runningAppProcessRepository.getRunningAppProcesses()

}