package com.example.airbagtest.interactors

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Context.USAGE_STATS_SERVICE
import com.example.airbagtest.database.model.RunningAppProcessCache
import com.example.airbagtest.database.model.toCache
import dagger.hilt.android.qualifiers.ApplicationContext
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
            time - TEN_SECONDS,
            time
        )

        return List(stats.size){ i ->
            stats[i].toCache()
        }
    }

    companion object{
        const val TEN_SECONDS = 10000
    }

}