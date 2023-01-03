package com.example.airbagtest.ui.ApplicationList

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.example.airbagtest.core.GetRunningAppProcessesWorker
import com.example.airbagtest.core.InsertRunningAppProcessesRemoteDataBaseWorker
import com.example.airbagtest.interactors.GetBackgroundProcessDataBaseUseCase
import com.example.airbagtest.ui.ApplicationList.state.ApplicationListUiState
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_LOCAL_DATABASE_WORK_NAME
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_REMOTE_DATABASE_WORK_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ApplicationListViewModel @Inject constructor(
    private val getBackgroundProcessDataBaseUseCase: GetBackgroundProcessDataBaseUseCase,
    private val workManager: WorkManager,
) : ViewModel() {

    private val _uiState: MutableLiveData<ApplicationListUiState> by lazy {
        MutableLiveData<ApplicationListUiState>()
    }

    val uiState: LiveData<ApplicationListUiState> = _uiState

    val outputWorkInfoItems: LiveData<List<WorkInfo>>

    init {
        runInsertAppProcessLocalDatabaseWorker()
        runInsertAppProcessRemoteDatabaseWorker()
        outputWorkInfoItems = workManager.getWorkInfosForUniqueWorkLiveData(
            INSERT_RUNNING_APP_PROCESS_LOCAL_DATABASE_WORK_NAME
        )
    }


    private fun workerConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()
    }

    private fun runInsertAppProcessRemoteDatabaseWorker() {
        val runInsertRemoteRequest = PeriodicWorkRequest.Builder(
            InsertRunningAppProcessesRemoteDataBaseWorker::class.java,
            MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS, // repeatInterval (the period cycle)
            MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS
        ).setConstraints(workerConstraints())
            .build()

        workManager.enqueueUniquePeriodicWork(
            INSERT_RUNNING_APP_PROCESS_REMOTE_DATABASE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            runInsertRemoteRequest
        )
    }

    private fun runInsertAppProcessLocalDatabaseWorker() {
        val runInsertLocalRequest = PeriodicWorkRequest.Builder(
            GetRunningAppProcessesWorker::class.java,
            MIN_PERIODIC_INTERVAL_MILLIS,
            TimeUnit.MILLISECONDS, // repeatInterval (the period cycle)
            MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS
        ).build()

        workManager.enqueueUniquePeriodicWork(
            INSERT_RUNNING_APP_PROCESS_LOCAL_DATABASE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            runInsertLocalRequest
        )


    }

    suspend fun requestBackgroundProcesses() {
        viewModelScope.launch {
            viewModelScope.launch {
                _uiState.value = ApplicationListUiState(
                    loading = true
                )

                _uiState.value =
                    ApplicationListUiState(
                        runningAppProcess = getBackgroundProcessDataBaseUseCase(),
                        loading = false
                    )
            }
        }

        fun screenLoading(isLoading: Boolean) {
            viewModelScope.launch {
                _uiState.value = ApplicationListUiState(
                    loading = isLoading
                )
            }
        }
    }
}