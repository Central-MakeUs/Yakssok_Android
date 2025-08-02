import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.yakssok.android.application)
}

android {
    namespace = "com.pillsquad.yakssok"

    defaultConfig {
        applicationId = "com.pillsquad.yakssok"
        versionCode = 3
        versionName = "0.0.3"
        targetSdk = 36

        buildConfigField("String", "KAKAO_API_KEY", getProperty("KAKAO_API_KEY"))
    }

    signingConfigs {
        create("release") {
            storeFile = file(getProperty("KEYSTORE_FILE"))
            storePassword = getProperty("KEYSTORE_PASSWORD")
            keyAlias = getProperty("KEY_ALIAS")
            keyPassword = getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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