import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.datastore")

dependencies {
    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.serialization.json)
}