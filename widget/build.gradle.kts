import com.pillsquad.yakssok.setNamespace

plugins {
    alias(libs.plugins.yakssok.android.library)
    alias(libs.plugins.yakssok.android.compose)
}

setNamespace("widget")

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.model)
    implementation(projects.core.designsystem)

    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
}