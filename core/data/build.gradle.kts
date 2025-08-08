import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.data")

android {
    buildTypes {
        debug {
            buildConfigField("String", "MASTER_ACCESS", getProperty("MASTER_ACCESS"))
            buildConfigField("String", "MASTER_REFRESH", getProperty("MASTER_REFRESH"))
        }
        release {
            buildConfigField("String", "MASTER_ACCESS", getProperty("MASTER_ACCESS"))
            buildConfigField("String", "MASTER_REFRESH", getProperty("MASTER_REFRESH"))
        }
    }
}

fun getProperty(key: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(key)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.core.sound)
    implementation(projects.core.firebase)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.exifinterface)

    implementation(libs.androidx.paging.runtime)
}