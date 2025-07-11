import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.routine")

dependencies {
    implementation(libs.kotlinx.datetime)
}