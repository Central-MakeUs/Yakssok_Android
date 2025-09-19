package com.pillsquad.yakssok.feature.mymate.model

import com.pillsquad.yakssok.core.model.User

sealed interface MyMateUiState {
    data object Loading : MyMateUiState
    data object Empty : MyMateUiState
    data class Success(val mateList: List<User> = emptyList()) : MyMateUiState
    data object Failure : MyMateUiState
}