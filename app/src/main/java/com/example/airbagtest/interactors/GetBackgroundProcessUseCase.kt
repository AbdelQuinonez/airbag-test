package com.example.airbagtest.interactors

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.USAGE_STATS_SERVICE
import android.util.Log
import com.example.airbagtest.database.model.RunningAppProcessCache
import com.example.airbagtest.database.model.toCache
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


class GetBackgroundProcessUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    operator fun invoke(): List<RunningAppProcessCache> {
        // Process running
        val mUsageStatsManager = context.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        /* We get usage stats for the last 10 seconds */
        val stats = mUsageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            time - 1000 * 10,
            time
        )

        return List(stats.size){ i ->
            val systemProcess = stats[i]
            RunningAppProcessCache(
                processName = systemProcess.packageName
            )
        }
    }

}