# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# keep class & interface associtaed by api
-keep class com.pillsquad.yakssok.core.network.model.** { *; }
-keep interface com.pillsquad.yakssok.core.network.service.** { *; }

# Retrofit
-keep interface retrofit2.* { *; }
-keep class retrofit2.Response

# Kotlin Serialization
-keep class kotlinx.serialization.** { *; }
-keepattributes *Annotation*
-dontwarn kotlinx.serialization.**

# Kotlin Reflection
-keep class kotlin.Metadata { *; }

# Coroutines
-dontwarn kotlinx.coroutines.**

# OkHttp
-keep class okhttp3.Callback { *; }
-dontwarn okhttp3.**

# Dagger / Hilt components
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class com.google.dagger.** { *; }
-keep class androidx.hilt.** { *; }
-keep class dagger.hilt.** { *; }
-dontwarn dagger.**
-dontwarn javax.inject.**
-dontwarn androidx.hilt.**
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

# ViewModels
-keep class * extends androidx.lifecycle.ViewModel
-keep class **ViewModel { *; }

# Jetpack Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Coil
-keep class coil.** { *; }
-keep class coil3.** { *; }
-dontwarn coil.**

# AndroidX Lifecycle / Navigation / DataStore
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**

# Firebase
-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**

# Kakao
-keep class com.kakao.sdk.** { *; }
-dontwarn com.kakao.sdk.**