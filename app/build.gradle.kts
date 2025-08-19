import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.yakssok.android.application)
    alias(libs.plugins.google)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.pillsquad.yakssok"

    defaultConfig {
        applicationId = "com.pillsquad.yakssok"
        versionCode = 15
        versionName = "1.0.2"
        targetSdk = 36

        buildConfigField("String", "KAKAO_API_KEY", getProperty("KAKAO_API_KEY"))
        resValue("string", "KAKAO_REDIRECT_URI", getProperty("KAKAO_REDIRECT_URI"))
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
            isShrinkResources = true
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
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.datastore)
    implementation(projects.core.network)
    implementation(projects.core.sound)
    implementation(projects.core.firebase)
    implementation(projects.core.push)

    implementation(projects.core.navigation)
    implementation(projects.core.designsystem)
    implementation(projects.core.ui)
    implementation(projects.core.common)

    implementation(projects.feature.main)
    implementation(projects.feature.alert)
    implementation(projects.feature.calendar)
    implementation(projects.feature.home)
    implementation(projects.feature.info)
    implementation(projects.feature.intro)
    implementation(projects.feature.mate)
    implementation(projects.feature.mymate)
    implementation(projects.feature.mypage)
    implementation(projects.feature.myroutine)
    implementation(projects.feature.profileEdit)
    implementation(projects.feature.routine)

    implementation(libs.kakao.user)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}