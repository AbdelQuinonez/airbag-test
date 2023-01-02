package com.example.airbagtest.ui.ApplicationList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.airbagtest.core.GetRunningAppProcessesWorker
import com.example.airbagtest.interactors.GetBackgroundProcessDataBaseUseCase
import com.example.airbagtest.interactors.InsertBackgroundProcessCacheDataBaseUseCase
import com.example.airbagtest.ui.ApplicationList.state.ApplicationListUiState
import com.example.airbagtest.utils.Constants.INSERT_RUNNING_APP_PROCESS_WORK_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ApplicationListViewModel @Inject constructor(
    private val insertBackgroundProcessCacheDataBaseUseCase: InsertBackgroundProcessCacheDataBaseUseCase,
    private val getBackgroundProcessDataBaseUseCase: GetBackgroundProcessDataBaseUseCase,
    private val workManager: WorkManager,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ApplicationListUiState> by lazy {
        MutableStateFlow(ApplicationListUiState())
    }

    val uiState: StateFlow<ApplicationListUiState> = _uiState

    val outputWorkInfoItems: LiveData<List<WorkInfo>>

    init {
        viewModelScope.launch{
            insertBackgroundProcessCacheDataBaseUseCase()
        }
        runService()
        outputWorkInfoItems = workManager.getWorkInfosForUniqueWorkLiveData(INSERT_RUNNING_APP_PROCESS_WORK_NAME)
    }

    private fun runService() {
        val runProcessesRequest = PeriodicWorkRequest.Builder(GetRunningAppProcessesWorker::class.java,
            15, TimeUnit.MINUTES, // repeatInterval (the period cycle)
            5, TimeUnit.SECONDS
        ).build()

        workManager.enqueueUniquePeriodicWork(
            INSERT_RUNNING_APP_PROCESS_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            runProcessesRequest
        )
    }

    suspend fun requestBackgroundProcesses() {
        viewModelScope.launch {
            _uiState.update {
                ApplicationListUiState(
                    loading = true
                )
            }

            _uiState.update {
                ApplicationListUiState(
                    runningAppProcess = getBackgroundProcessDataBaseUseCase(),
                    loading = false
                )
            }
        }
    }
}