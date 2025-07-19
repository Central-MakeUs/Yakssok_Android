import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

android {
    buildTypes {
        debug {
            buildConfigField("String", "PRIVATE_POLICY", getProperty("privacyPolicy"))
            buildConfigField("String", "USAGE_POLICY", getProperty("usagePolicy"))
        }
        release {
            buildConfigField("String", "PRIVATE_POLICY", getProperty("privacyPolicy"))
            buildConfigField("String", "USAGE_POLICY", getProperty("usagePolicy"))
        }
    }
}

fun getProperty(key: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(key)
}

setNamespace("feature.mypage")