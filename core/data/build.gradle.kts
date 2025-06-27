import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.data")

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.datasourceApi)

    implementation(libs.kotlinx.serialization.json)
}