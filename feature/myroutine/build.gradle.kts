import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.myroutine")

dependencies {
    implementation(libs.kotlinx.datetime)
}