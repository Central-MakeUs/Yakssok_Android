import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.feature)
}

setNamespace("feature.main")

dependencies {
    implementation(libs.kotlinx.immutable)
    implementation(libs.app.update)
    implementation(libs.app.update.ktx)

    implementation(projects.feature.home)
    implementation(projects.feature.intro)
    implementation(projects.feature.routine)
    implementation(projects.feature.alert)
    implementation(projects.feature.mate)
    implementation(projects.feature.info)
    implementation(projects.feature.mypage)
    implementation(projects.feature.profileEdit)
    implementation(projects.feature.myroutine)
    implementation(projects.feature.mymate)
    implementation(projects.feature.calendar)
}