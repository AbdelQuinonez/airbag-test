package com.example.airbagtest.ui.ApplicationList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.example.airbagtest.core.GetRunningAppProcessesWorker
import com.example.airbagtest.core.InsertRunningAppProcessesRemoteDataBaseWorker
import com.example.airbagtest.interactors.GetBackgroundProcessCacheToDomainUseCase
import com.example.airbagtest.ui.ApplicationList.state.ApplicationListUiState
import com.example.airbagtest.utils.AppDispatcher
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_LOCAL_DATABASE_WORK_NAME
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_REMOTE_DATABASE_WORK_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class ApplicationListViewModel @Inject constructor(
    private val getBackgroundProcessCacheToDomainUseCase: GetBackgroundProcessCacheToDomainUseCase,
    private val workManager: WorkManager,
    private val dispatcher: AppDispatcher
) : ViewModel() {

    private val _uiState: MutableLiveData<ApplicationListUiState> by lazy {
        MutableLiveData()
    }

    val uiState: LiveData<ApplicationListUiState> = _uiState

    @Volatile
    private var applicationListUiState = ApplicationListUiState()

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
        viewModelScope.launch(dispatcher.io()) {
            _uiState.postValue(
                applicationListUiState.copy(
                    runningAppProcess = getBackgroundProcessCacheToDomainUseCase(),
                )
            )
        }
    }

    fun hasPermissions(value: Boolean) {
        _uiState.postValue(
            applicationListUiState.copy(
                hasPermissions = value
            )
        )

    }


}