package com.example.airbagtest.interactors

import com.example.airbagtest.database.model.RunningAppProcessCache
import com.example.airbagtest.repository.RunningAppProcessRepository
import javax.inject.Inject

class GetBackgroundProcessCacheUseCase @Inject constructor(
    private val runningAppProcessRepository: RunningAppProcessRepository
) {
    suspend operator fun invoke(): List<RunningAppProcessCache> =
        runningAppProcessRepository.getRunningAppProcesses()
}