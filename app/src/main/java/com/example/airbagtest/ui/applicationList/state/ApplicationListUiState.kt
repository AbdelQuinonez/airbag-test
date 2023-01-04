package com.example.airbagtest.ui.applicationList.state

import com.example.airbagtest.model.RunningAppProcess

data class ApplicationListUiState(
    val runningAppProcess: List<RunningAppProcess> = listOf(),
)