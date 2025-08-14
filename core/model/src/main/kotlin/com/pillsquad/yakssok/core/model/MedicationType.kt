package com.pillsquad.yakssok.core.model

enum class MedicationType(val krName: String, val color: Long) {
    SUPPLEMENT("건강 기능 식품/영양보충", 0xFFDB7C24),
    CHRONIC("만성 질환 관리", 0xFF40B0FA),
    BEAUTY("미용 관련 관리", 0xFF3ADE4D),
    DIET("다이어트/대사 관련", 0xFFD224DB),
    TEMPORARY("통증/감기 등 일시적 치료", 0xFFE89F18),
    MENTAL("정신 건강 관리", 0xFF7C24DB),
    OTHER("기타 설정", 0xFFDB2427),
}