import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.intro")

dependencies {
    implementation(libs.kakao.user)
}