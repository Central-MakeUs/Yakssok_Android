package com.pillsquad.yakssok

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCoroutineAndroid() {
    val libs = extensions.libs
    configureCoroutineKotlin()
    dependencies {
        add("implementation", libs.findLibrary("coroutines.android").get())
    }
}

internal fun Project.configureCoroutineKotlin() {
    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("coroutines.core").get())
        add("testImplementation", libs.findLibrary("coroutines.test").get())
    }
}