package com.pillsquad.yakssok

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureJUnitAndroid() {
    val libs = extensions.libs

    dependencies {
        add("implementation", libs.findLibrary("junit4").get())
        add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
    }
}