
plugins {
    alias(libs.plugins.yakssok.android.application)
}

android {
    namespace = "com.pillsquad.yakssok"

    defaultConfig {
        applicationId = "com.pillsquad.yakssok"
        versionCode = 1
        versionName = "1.0"
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

dependencies {
    implementation(projects.feature.main)
    implementation(projects.core.data)
    implementation(projects.core.network)
}