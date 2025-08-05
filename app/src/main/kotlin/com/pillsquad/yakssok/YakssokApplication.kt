package com.pillsquad.yakssok

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.common.KakaoSdk
import com.pillsquad.yakssok.core.common.AppInfo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YakssokApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppInfo.APP_VERSION = BuildConfig.VERSION_NAME

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
    }
}