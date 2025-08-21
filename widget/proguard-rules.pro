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

-keep public class * extends androidx.glance.appwidget.action.ActionCallback
-keepnames class * extends androidx.glance.appwidget.action.ActionCallback

-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.InputMerger
-keep class * extends androidx.work.ListenableWorker
-keepclassmembers class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}
-keep class androidx.work.WorkerParameters
-keep class com.pillsquad.yakssok.widget.worker.** { *; }

-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep Dependency Injection Framework related classes and methods
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.annotation.** { *; }
-keep class javax.lang.model.** { *; }
-keep class * implements dagger.internal.Factory { *; }
-keep class * implements javax.inject.Provider { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }
-dontwarn javax.lang.model.**