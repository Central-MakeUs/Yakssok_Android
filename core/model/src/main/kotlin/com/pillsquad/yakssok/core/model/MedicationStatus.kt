package com.pillsquad.yakssok.core.model

enum class MedicationStatus(val krName: String) {
    PLANNED("복약 전"), TAKING("복약 중"), ENDED("복약 종료")
}