package com.example.airbagtest.domain.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.airbagtest.interactors.InsertBackgroundProcessCacheDataBaseUseCase
import com.example.airbagtest.utils.AppDispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class GetRunningAppProcessesWorker @AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val insertBackgroundProcessCacheDataBaseUseCase: InsertBackgroundProcessCacheDataBaseUseCase,
    private val dispatcher: AppDispatcher,
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        withContext(dispatcher.io()) {
            insertBackgroundProcessCacheDataBaseUseCase()
            return@withContext Result.success()
        }

}