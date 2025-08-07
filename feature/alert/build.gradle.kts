import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.alert")

dependencies {
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
}