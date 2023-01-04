package com.example.airbagtest.utils

import android.app.AlertDialog
import android.content.Context

class SimpleDialogUtility(private val context: Context) {

    private val hasPermission: Boolean = PermissionUtility.hasPackageUsageStatsPermissions(context)


    fun showDialog() {
        val alertDialog: AlertDialog = context.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getDialogTitle())
            builder.setMessage(getBodyText())
            builder.apply {
                setPositiveButton(
                    POSITIVE_BUTTON
                ) { _, _ ->
                }
            }
            builder.create()
        }
        alertDialog.show()
    }


    private fun getDialogTitle(): String {
        return if (hasPermission) {
            PERMISSION_GRANTED_TITLE
        } else
            PERMISSION_DENIED_TITLE
    }

    private fun getBodyText(): String {
        return if (hasPermission) {
            PERMISSION_GRANTED_BODY
        } else
            PERMISSION_DENIED_BODY
    }

    companion object DialogConstants {
        const val PERMISSION_GRANTED_TITLE = "Congratulations"
        const val PERMISSION_GRANTED_BODY = "You have permission to retrieve system data"
        const val PERMISSION_DENIED_TITLE = "Oh no.."
        const val PERMISSION_DENIED_BODY = "You don\'t have permission to retrieve system data"
        const val POSITIVE_BUTTON = "Ok"
    }

}