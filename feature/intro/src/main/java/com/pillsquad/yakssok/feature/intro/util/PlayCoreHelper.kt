package com.pillsquad.yakssok.feature.intro.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.core.net.toUri
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.UpdateAvailability

// FLEXIBLE = 0
// IMMEDIATE = 1
internal fun startUpdate(
    activity: Activity,
    type: Int,
    onUpdateNotAvailable: () -> Unit
) {
    val manager = AppUpdateManagerFactory.create(activity)
    manager.appUpdateInfo.addOnSuccessListener { info ->
        if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
            info.isUpdateTypeAllowed(type)
        ) {
            manager.startUpdateFlowForResult(
                info,
                activity,
                AppUpdateOptions.defaultOptions(type),
                1234
            )
        } else {
            onUpdateNotAvailable()
        }
    }.addOnFailureListener { e ->
        onUpdateNotAvailable()
    }
}

internal fun openPlayStore(activity: Activity) {
    val marketIntent = Intent(
        Intent.ACTION_VIEW,
        "market://details?id=${activity.packageName}".toUri()
    )
    marketIntent.setPackage("com.android.vending")
    try {
        activity.startActivity(marketIntent)
    } catch (e: ActivityNotFoundException) {
        val webIntent = Intent(
            Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=${activity.packageName}".toUri()
            )
        )
        activity.startActivity(webIntent)
    }
}