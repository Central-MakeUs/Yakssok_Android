import com.pillsquad.yakssok.setNamespace
plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.yakssok.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.navigation")

dependencies {
    implementation(libs.kotlinx.serialization.json)
}