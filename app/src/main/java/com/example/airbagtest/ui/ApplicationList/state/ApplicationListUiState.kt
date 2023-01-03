package com.example.airbagtest.ui.ApplicationList.state

import com.example.airbagtest.model.RunningAppProcess

data class ApplicationListUiState(
    val loading: Boolean = false,
    val runningAppProcess: List<RunningAppProcess> = listOf(),
    val hasPermissions : Boolean = false
)