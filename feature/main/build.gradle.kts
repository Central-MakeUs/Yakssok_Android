import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.main")

android {
    defaultConfig {
        buildConfigField("String", "KAKAO_API_KEY", getProperty("KAKAO_API_KEY"))
        resValue("string", "KAKAO_REDIRECT_URI", getProperty("KAKAO_REDIRECT_URI"))
    }
}

fun getProperty(key: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(key)
}

dependencies {
    implementation(libs.kotlinx.immutable)

    implementation(projects.feature.home)
    implementation(projects.feature.intro)
    implementation(projects.feature.routine)
    implementation(projects.feature.alert)
    implementation(projects.feature.mate)
    implementation(projects.feature.info)
    implementation(projects.feature.mypage)
    implementation(projects.feature.profileEdit)
    implementation(projects.feature.myroutine)
    implementation(projects.feature.mymate)
    implementation(projects.feature.calendar)
}