package com.pillsquad.yakssok.feature.mymate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.domain.usecase.GetFollowerListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetFollowingListUseCase
import com.pillsquad.yakssok.feature.mymate.model.MyMateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyMateViewModel @Inject constructor(
    private val getFollowingListUseCase: GetFollowingListUseCase,
    private val getFollowerListUseCase: GetFollowerListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyMateUiModel())
    val uiState = _uiState.asStateFlow()

    init {
        getMateList()
    }

    fun getMateList() {
        viewModelScope.launch {
            val followingDeferred = async {
                getFollowingListUseCase()
            }
            val followerDeferred = async {
                getFollowerListUseCase()
            }

            val (followingResult, followerResult) = awaitAll(followingDeferred, followerDeferred)

            followingResult.onSuccess {
                _uiState.value = _uiState.value.copy(followingList = it)
            }.onFailure {
                it.printStackTrace()
                Log.e("MyMateViewModel", "getMateList: $it")
            }

            followerResult.onSuccess {
                _uiState.value = _uiState.value.copy(followerList = it)
            }.onFailure {
                it.printStackTrace()
                Log.e("MyMateViewModel", "getMateList: $it")
            }
        }
    }

}