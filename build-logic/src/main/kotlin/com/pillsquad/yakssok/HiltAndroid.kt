package com.pillsquad.yakssok

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("com.google.devtools.ksp")
    }

    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("hilt.android").get())
        add("ksp", libs.findLibrary("hilt.android.compiler").get())
        val androidTestKsp = configurations.findByName("kspAndroidTest")
        if (androidTestKsp != null) {
            add("kspAndroidTest", libs.findLibrary("hilt.android.compiler").get())
        }
    }
}