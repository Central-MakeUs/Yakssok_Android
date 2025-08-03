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

# Retrofit
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# Kotlin Serialization
-keepclassmembers class kotlinx.serialization.** { *; }
-keepattributes *Annotation*

# Kotlin Reflection
-keepclassmembers class kotlin.Metadata { *; }
-keepclassmembers class ** {
    @com.kakao.sdk.common.json.* <fields>;
}

# Coroutines
-dontwarn kotlinx.coroutines.**

# OkHttp
-dontwarn okhttp3.**

# Dagger / Hilt components
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class com.google.dagger.** { *; }
-keep class androidx.hilt.** { *; }
-dontwarn dagger.**
-dontwarn javax.inject.**
-dontwarn androidx.hilt.**

# Hilt Generated Components
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

# ViewModel (HiltViewModel 포함)
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
-keep class com.kakao.sdk.**.model.* { <fields>; }