# Keep Hilt modules
-keep class com.pillsquad.yakssok.core.network.di.** { *; }
-keep @interface com.pillsquad.yakssok.core.network.di.*

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
-keep class com.pillsquad.yakssok.core.network.calladapter.** { *; }
-keep interface com.pillsquad.yakssok.core.network.interceptor.** { *; }
