package com.example.airbagtest.model

import android.app.ActivityManager
import com.example.airbagtest.database.model.RunningAppProcessCache


data class RunningAppProcess(
    val processName : String = ""
)

fun ActivityManager.RunningAppProcessInfo.toDomain(): RunningAppProcess =
    RunningAppProcess(
        processName = this.processName,
    )

fun RunningAppProcess.fromDomainToCache(): RunningAppProcessCache =
    RunningAppProcessCache(
        processName = this.processName,
    )