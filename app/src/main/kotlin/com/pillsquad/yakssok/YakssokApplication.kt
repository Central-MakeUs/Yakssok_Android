package com.pillsquad.yakssok

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.common.KakaoSdk
import com.pillsquad.yakssok.core.common.AppInfo
import com.pillsquad.yakssok.core.push.ChannelRegistry
import com.pillsquad.yakssok.widget.worker.scheduleWidgetSync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class YakssokApplication : Application() {
    @Inject lateinit var channelRegistry: ChannelRegistry

    override fun onCreate() {
        super.onCreate()

        AppInfo.VERSION_NAME = BuildConfig.VERSION_NAME
        AppInfo.VERSION_CODE = BuildConfig.VERSION_CODE

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
        channelRegistry.ensureChannels()

        scheduleWidgetSync(this)
    }
}