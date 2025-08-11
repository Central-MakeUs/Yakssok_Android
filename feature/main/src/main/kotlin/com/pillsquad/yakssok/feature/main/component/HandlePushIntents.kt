package com.pillsquad.yakssok.feature.main.component

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.pillsquad.yakssok.core.push.model.PushIntentKeys
import com.pillsquad.yakssok.feature.main.MainNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun HandlePushIntents(
    intents: Flow<Intent>,
    navigator: MainNavigator
) {
    LaunchedEffect(Unit) {
        intents.collectLatest { intent ->
            when (intent.getStringExtra(PushIntentKeys.TARGET)) {
                "home"   -> navigator.navigateHome()
                "alert"  -> navigator.navigateAlert()
                "routine"-> navigator.navigateRoutine()
                "mypage" -> navigator.navigateMyPage()
                "mate"   -> navigator.navigateMate()
            }
            intent.removeExtra(PushIntentKeys.TARGET)
            intent.removeExtra(PushIntentKeys.PAYLOAD)
        }
    }
}