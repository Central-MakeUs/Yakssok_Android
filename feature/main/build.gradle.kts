import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.main")

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.intro)
    implementation(projects.feature.routine)

    implementation(libs.kotlinx.immutable)
}