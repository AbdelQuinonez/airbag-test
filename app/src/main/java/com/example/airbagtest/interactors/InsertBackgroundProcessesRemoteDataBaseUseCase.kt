package com.example.airbagtest.interactors

import com.example.airbagtest.remote.RunningAppProcessRemoteRepository
import javax.inject.Inject

class InsertBackgroundProcessesRemoteDataBaseUseCase @Inject constructor(
    private val getBackgroundProcessDataBaseUseCase: GetBackgroundProcessDataBaseUseCase,
    private val runningAppProcessRemoteRepository: RunningAppProcessRemoteRepository
) {

    suspend operator fun invoke(): Boolean {
        val processesList = getBackgroundProcessDataBaseUseCase.getCacheList()
        return runningAppProcessRemoteRepository.insert(processesList)
    }

}