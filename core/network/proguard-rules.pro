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

# Keep Hilt modules
-keep class com.pillsquad.yakssok.core.network.di.** { *; }

# Keep network service interfaces & class
-keep class com.pillsquad.yakssok.core.network.model.** { *; }
-keep class com.pillsquad.yakssok.core.network.service.** { *; }
-keep interface com.pillsquad.yakssok.core.network.service.** { *; }

# Keep qualifiers (prevent renaming)
-keep class com.pillsquad.yakssok.core.network.di.TokenInterceptorHttpClient
-keep class com.pillsquad.yakssok.core.network.di.NoHeaderHttpClient
-keep class com.pillsquad.yakssok.core.network.di.TokenRetrofit
-keep class com.pillsquad.yakssok.core.network.di.NoHeaderRetrofit

# Keep interceptors and call adapters
-keep class com.pillsquad.yakssok.core.network.interceptor.** { *; }
-keep interface com.pillsquad.yakssok.core.network.interceptor.** { *; }
-keep class com.pillsquad.yakssok.core.network.calladapter.** { *; }