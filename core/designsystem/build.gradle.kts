import com.pillsquad.yakssok.setNamespace
plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.yakssok.android.compose)
}

setNamespace("core.designsystem")

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)
}