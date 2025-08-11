import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
}

setNamespace("core.push")

dependencies {
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.messaging.ktx)

    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.sound)
}