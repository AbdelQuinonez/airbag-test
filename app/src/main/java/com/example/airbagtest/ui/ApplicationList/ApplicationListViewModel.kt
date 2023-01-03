package com.example.airbagtest.ui.ApplicationList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.example.airbagtest.core.GetRunningAppProcessesWorker
import com.example.airbagtest.core.InsertRunningAppProcessesRemoteDataBaseWorker
import com.example.airbagtest.interactors.GetBackgroundProcessCacheToDomainUseCase
import com.example.airbagtest.ui.ApplicationList.state.ApplicationListUiState
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_LOCAL_DATABASE_WORK_NAME
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_REMOTE_DATABASE_WORK_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ApplicationListViewModel @Inject constructor(
    private val getBackgroundProcessCacheToDomainUseCase: GetBackgroundProcessCacheToDomainUseCase,
    private val workManager: WorkManager,
) : ViewModel() {

    private val _uiState: MutableSharedFlow<ApplicationListUiState> = MutableSharedFlow()

    val uiState: SharedFlow<ApplicationListUiState> = _uiState

    private val applicationListUiState = ApplicationListUiState()

    init {
        runInsertAppProcessLocalDatabaseWorker()
        runInsertAppProcessRemoteDatabaseWorker()
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

            _uiState.emit(
                applicationListUiState.copy(
                    loading = true
                )
            )

            _uiState.emit(
                applicationListUiState.copy(
                    runningAppProcess = getBackgroundProcessCacheToDomainUseCase(),
                    loading = false
                )
            )
        }
    }

    suspend fun hasPermissions(value: Boolean) {
        viewModelScope.launch {
            _uiState.emit(
                applicationListUiState.copy(
                    hasPermissions = value
                )
            )
        }
    }


}