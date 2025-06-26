package com.pillsquad.yakssok

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.pillsquad.yakssok.$name"
    }
}