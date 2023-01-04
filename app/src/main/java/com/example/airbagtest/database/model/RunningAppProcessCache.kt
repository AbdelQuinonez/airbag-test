package com.example.airbagtest.database.model

import android.app.usage.UsageStats
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.airbagtest.domain.model.RunningAppProcess
import com.example.airbagtest.remote.model.RunningAppProcessRemote

@Entity
data class RunningAppProcessCache(
    @PrimaryKey var processName: String = "",
)


fun RunningAppProcessCache.fromCacheToDomain(): RunningAppProcess =
    RunningAppProcess(
        processName = this.processName,
    )


fun RunningAppProcessCache.fromCacheToRemote(): RunningAppProcessRemote =
    RunningAppProcessRemote(
        processName = this.processName,
    )

fun UsageStats.toRemote(): RunningAppProcessCache =
    RunningAppProcessCache(
        processName = this.packageName
    )


