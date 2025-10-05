package com.pillsquad.yakssok.core.ui.ext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.core.net.toUri

fun openPlayStore(activity: Activity) {
    val marketIntent = Intent(
        Intent.ACTION_VIEW,
        "market://details?id=${activity.packageName}".toUri()
    ).apply {
        setPackage("com.android.vending")
    }

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