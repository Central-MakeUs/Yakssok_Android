package com.pillsquad.yakssok.core.model

enum class UpdateType {
    NONE,       // 업데이트 필요 없음
    SOFT,       // 권장 업데이트
    FORCE,      // 강제 업데이트
    NETWORK,    // 네트워크 에러
    ERROR,      // 이외의 에러
}