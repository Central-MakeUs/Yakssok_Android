package com.pillsquad.yakssok.widget

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.pillsquad.yakssok.core.domain.usecase.widget.MarkTakenAndRefreshUseCase
import com.pillsquad.yakssok.core.domain.usecase.widget.ObserveWidgetSnapshotUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first

class MarkTakenAction : ActionCallback {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface Deps {
        fun observeUC(): ObserveWidgetSnapshotUseCase
        fun markUC(): MarkTakenAndRefreshUseCase
    }
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val deps = EntryPointAccessors.fromApplication(context, Deps::class.java)
        val snapshot = deps.observeUC().invoke().first()
        val id = snapshot.nextRoutineId ?: return

        deps.markUC().invoke(id)
            .onSuccess {
                YakssokWidget().update(context, glanceId)
            }.onFailure {
                Log.e("MarkTakenAction", "failed to mark taken", it)
            }
    }
}