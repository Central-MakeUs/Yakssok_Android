import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.kotlin.serialization)
}

setNamespace("core.firebase")

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.google.play.service)
    implementation(libs.coroutines.play.services)
}