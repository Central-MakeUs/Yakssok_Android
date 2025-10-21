package com.pillsquad.yakssok.feature.intro.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.core.net.toUri
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

// FLEXIBLE = 0
// IMMEDIATE = 1
internal fun startUpdate(
    activity: Activity,
    type: Int,
    onUpdateNotAvailable: () -> Unit
) {
    val manager = AppUpdateManagerFactory.create(activity)

    lateinit var listener: InstallStateUpdatedListener

    listener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> manager.completeUpdate()
            InstallStatus.INSTALLED -> manager.unregisterListener(listener)
            else -> Unit
        }
    }

    if (type == 0) manager.registerListener(listener)

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