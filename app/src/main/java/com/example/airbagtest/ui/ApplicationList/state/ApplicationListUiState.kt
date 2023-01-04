package com.example.airbagtest.ui.ApplicationList.state

import com.example.airbagtest.model.RunningAppProcess

data class ApplicationListUiState(
    val runningAppProcess: List<RunningAppProcess> = listOf(),
    val hasPermissions : Boolean = false
)