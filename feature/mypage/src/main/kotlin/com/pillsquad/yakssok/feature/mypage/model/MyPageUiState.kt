package com.pillsquad.yakssok.feature.mypage.model

sealed class MyPageUiState {
    object Loading : MyPageUiState()
    data class Success(val data: MyPageUiModel) : MyPageUiState()
    data class Error(val message: String) : MyPageUiState()
}
