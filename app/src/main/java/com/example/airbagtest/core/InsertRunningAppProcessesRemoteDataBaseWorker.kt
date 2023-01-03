package com.example.airbagtest.core

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.airbagtest.interactors.InsertBackgroundProcessesRemoteDataBaseUseCase
import com.example.airbagtest.utils.AppDispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class InsertRunningAppProcessesRemoteDataBaseWorker @AssistedInject
constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val insertBackgroundProcessesRemoteDataBaseUseCase: InsertBackgroundProcessesRemoteDataBaseUseCase,
    private val dispatcher: AppDispatcher,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        withContext(dispatcher.io()) {
            val value = insertBackgroundProcessesRemoteDataBaseUseCase()
            when(value){
                true -> return@withContext Result.success()
                false -> return@withContext Result.failure()
            }
        }

}