plugins {
    alias(libs.plugins.yakssok.kotlin.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(projects.core.model)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}
