import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.domain")

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}