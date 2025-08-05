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

-keep class com.pillsquad.yakssok.core.network.interceptor.** { *; }
-keep class com.pillsquad.yakssok.core.network.calladapter.** { *; }

-keep class com.pillsquad.yakssok.core.network.di.** { *; }
-keep @interface com.pillsquad.yakssok.core.network.di.*

-keep class com.pillsquad.yakssok.core.network.model.** { *; }
-keep interface com.pillsquad.yakssok.core.network.service.** { *; }

# Keep Dependency Injection Framework related classes and methods
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class javax.lang.model.** { *; }
-keep class * implements dagger.internal.Factory { *; }
-keep class * implements javax.inject.Provider { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-dontwarn javax.lang.model.**

-keep class com.kakao.sdk.**.model.* { <fields>; }

# https://github.com/square/okhttp/pull/6792
-dontwarn org.bouncycastle.jsse.**
-dontwarn org.conscrypt.*
-dontwarn org.openjsse.**

# refrofit2 (with r8 full mode)
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
-keep,allowobfuscation,allowshrinking class retrofit2.Response