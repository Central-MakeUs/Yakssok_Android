import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.yakssok.android.application)
}

android {
    namespace = "com.pillsquad.yakssok"

    defaultConfig {
        applicationId = "com.pillsquad.yakssok"
        versionCode = 1
        versionName = "1.0"
        targetSdk = 36

        buildConfigField("String", "KAKAO_API_KEY", getProperty("KAKAO_API_KEY"))
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

fun getProperty(key: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(key)
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.core.data)
    implementation(projects.core.network)

    implementation(libs.kakao.user)
}