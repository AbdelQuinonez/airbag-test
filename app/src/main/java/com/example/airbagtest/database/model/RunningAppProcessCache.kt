package com.example.airbagtest.database.model

import android.app.ActivityManager
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.airbagtest.model.RunningAppProcess

@Entity
data class RunningAppProcessCache(
    @PrimaryKey var processName: String = "",
)

fun ActivityManager.RunningAppProcessInfo.toCache(): RunningAppProcessCache =
    RunningAppProcessCache(
        processName = this.processName,
    )

fun RunningAppProcessCache.fromCacheToDomain(): RunningAppProcess{
    return RunningAppProcess(
        processName = this.processName,
    )
}