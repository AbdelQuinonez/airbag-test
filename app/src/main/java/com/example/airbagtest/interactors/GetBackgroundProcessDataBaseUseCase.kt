package com.example.airbagtest.interactors

import com.example.airbagtest.model.RunningAppProcess
import com.example.airbagtest.repository.RunningAppProcessRepository
import javax.inject.Inject


class GetBackgroundProcessDataBaseUseCase @Inject constructor(
    private val runningAppProcessRepository: RunningAppProcessRepository
) {

    suspend operator fun invoke(): List<RunningAppProcess>{
        val cacheList = runningAppProcessRepository.getRunningAppProcesses()
        return List(cacheList.size){ i ->
            val cacheProcess = cacheList[i]
            RunningAppProcess(
                processName = cacheProcess.processName,
            )
        }
    }

}