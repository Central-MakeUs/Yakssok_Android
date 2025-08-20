package com.pillsquad.yakssok.widget

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.appwidget.action.actionStartActivity

@Composable
internal fun launchAppAction(): Action {
    val ctx = LocalContext.current
    val intent = ctx.packageManager.getLaunchIntentForPackage(ctx.packageName)
    return actionStartActivity(intent ?: Intent(Intent.ACTION_VIEW))
}