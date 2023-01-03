package com.example.airbagtest.utils

import android.app.AppOpsManager
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import android.content.Context
import android.os.Build
import android.os.Process


object PermissionUtility {

    fun hasPackageUsageStatsPermissions(context: Context): Boolean {
        val appOps = context
            .getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                OPSTR_GET_USAGE_STATS,
                Process.myUid(), context.packageName
            )
        } else {
            appOps.checkOpNoThrow(
                OPSTR_GET_USAGE_STATS,
                Process.myUid(), context.packageName
            );
        }

        return mode == AppOpsManager.MODE_ALLOWED
    }


}