package com.example.airbagtest.interactors

import com.example.airbagtest.repository.RunningAppProcessRepository
import javax.inject.Inject

class InsertBackgroundProcessCacheDataBaseUseCase @Inject constructor(
    private val getBackgroundProcessUseCase: GetBackgroundProcessUseCase,
    private val runningAppProcessRepository: RunningAppProcessRepository
) {
    suspend operator fun invoke():List<Long>{
        val processesList = getBackgroundProcessUseCase()
        return runningAppProcessRepository.insertRunningAppProcesses(processesList)
    }
}