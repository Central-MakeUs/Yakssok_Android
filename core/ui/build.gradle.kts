import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.yakssok.android.compose)
}

setNamespace("core.ui")

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.common)

    implementation(libs.kotlinx.datetime)
}