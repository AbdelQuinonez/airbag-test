package com.example.airbagtest.core

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.airbagtest.interactors.InsertBackgroundProcessCacheDataBaseUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class GetRunningAppProcessesWorker @AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val insertBackgroundProcessCacheDataBaseUseCase: InsertBackgroundProcessCacheDataBaseUseCase
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            insertBackgroundProcessCacheDataBaseUseCase()
            return@withContext Result.success()
        }

}