package com.pillsquad.yakssok.feature.mymate

import androidx.lifecycle.ViewModel
import com.pillsquad.yakssok.core.model.Mate
import com.pillsquad.yakssok.feature.mymate.model.MyMateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyMateViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(MyMateUiModel())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = MyMateUiModel(
            followingList = listOf(
                Mate(
                    id = 1,
                    name = "임용수",
                    nickName = "나",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 1
                ),
                Mate(
                    id = 2,
                    name = "조앵",
                    nickName = "PM",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 0
                ),
                Mate(
                    id = 3,
                    name = "리아",
                    nickName = "iOS",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 3
                ),
                Mate(
                    id = 4,
                    name = "노을",
                    nickName = "Server",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 3
                )
            ),
            followerList = listOf(
                Mate(
                    id = 1,
                    name = "임수",
                    nickName = "나",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 1
                ),
                Mate(
                    id = 2,
                    name = "앵",
                    nickName = "PM",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 0
                ),
                Mate(
                    id = 3,
                    name = "아",
                    nickName = "iOS",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 3
                ),
                Mate(
                    id = 4,
                    name = "노을",
                    nickName = "Server",
                    profileImage = "https://picsum.photos/200",
                    remainedMedicine = 3
                )
            ),
        )
    }
}