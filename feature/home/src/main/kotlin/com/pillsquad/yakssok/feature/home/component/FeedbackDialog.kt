package com.pillsquad.yakssok.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pillsquad.yakssok.core.designsystem.component.YakssokButton
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.component.YakssokTextField
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.component.DailyMedicineRow

@Composable
internal fun FeedbackDialog(
    user: User,
    todayRoutineCount: Int? = null,
    routineList: List<Routine>,
    onDismiss: () -> Unit,
    onConfirm: (Int, String, String) -> Unit
) {
    val isNagging = todayRoutineCount == null
    val hintText = if (isNagging) "한 줄 잔소리" else "한 줄 칭찬"

    val textList = if (isNagging) {
        listOf(
            "얼른 먹어요!", "약 놓쳤어요!", "건강 챙겨요!", "먹을 때까지 숨 참을게요 흡!"
        )
    } else {
        listOf(
            "정말 대단하군!", "오늘도 건강 챙기기 완료네요!", "야무진 당신", "칭찬 드려요 짱!"
        )
    }

    var selectedTextIdx by remember { mutableStateOf<Int?>(null) }
    var feedbackMessage by remember { mutableStateOf("") }
    var enabled by remember { mutableStateOf(false) }

    val confirmColor = when {
        isNagging && enabled -> YakssokTheme.color.primary400
        !isNagging && enabled -> YakssokTheme.color.subBlue
        else -> YakssokTheme.color.grey200
    }
    val contentColor = if (enabled) YakssokTheme.color.grey50 else YakssokTheme.color.grey400

    LaunchedEffect(selectedTextIdx, feedbackMessage) {
        enabled = selectedTextIdx != null || feedbackMessage.isNotBlank()
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .background(
                        color = YakssokTheme.color.grey50,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(top = 12.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .imePadding(),
            ) {
                Spacer(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(4.dp)
                        .width(38.dp)
                        .background(color = Color(0xFFDBDBDB))
                )

                Spacer(modifier = Modifier.height(32.dp))

                DialogInfoItem(user, isNagging, todayRoutineCount)

                Spacer(modifier = Modifier.height(20.dp))

                DialogContent(
                    isNagging = isNagging,
                    routineList = routineList,
                    textList = textList,
                    selectedTextIdx = selectedTextIdx,
                    hintText = hintText,
                    feedbackMessage = feedbackMessage,
                    onSelectedTextChange = {
                        selectedTextIdx = it
                        feedbackMessage = ""
                    },
                    onFeedbackMessageChange = {
                        feedbackMessage = it
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    YakssokButton(
                        modifier = Modifier.weight(1f),
                        text = "닫기",
                        backgroundColor = YakssokTheme.color.grey100,
                        onClick = onDismiss
                    )
                    YakssokButton(
                        modifier = Modifier.weight(2.7f),
                        text = "전송",
                        contentColor = contentColor,
                        backgroundColor = confirmColor,
                        enabled = enabled,
                        onClick = {
                            val message = feedbackMessage.ifEmpty {
                                textList[selectedTextIdx ?: 0]
                            }
                            val type = if (isNagging) "nag" else "praise"
                            onConfirm(user.id, message, type)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogContent(
    isNagging: Boolean,
    routineList: List<Routine>,
    textList: List<String>,
    selectedTextIdx: Int?,
    hintText: String,
    feedbackMessage: String,
    onSelectedTextChange: (Int?) -> Unit,
    onFeedbackMessageChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (routineList.size > 3) {
            val itemHeight = 56.dp
            val maxVisibleItems = 4
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .height(itemHeight * maxVisibleItems + 24.dp)
                    .verticalScroll(scrollState)
            ) {
                routineList.forEach {
                    DailyMedicineRow(
                        routine = it,
                        isFeedback = true,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else {
            routineList.forEach {
                DailyMedicineRow(
                    routine = it,
                    isFeedback = true,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(color = YakssokTheme.color.grey200)

        Spacer(modifier = Modifier.height(20.dp))

        SelectFeedbackRow(
            textList = textList,
            isNagging = isNagging,
            selectedTextIdx = selectedTextIdx,
            onTextChange = {
                if (feedbackMessage.isEmpty()) {
                    onSelectedTextChange(it)
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        YakssokTextField(
            modifier = Modifier.fillMaxWidth(),
            value = feedbackMessage,
            onValueChange = {
                if (it.isNotBlank()) onSelectedTextChange(null)
                onFeedbackMessageChange(it)
            },
            hint = hintText,
            maxLength = 15,
            isShowCounter = true,
            isShowClear = true
        )
    }
}

@Composable
private fun DialogInfoItem(
    user: User,
    isNagging: Boolean,
    todayRoutineCount: Int? = null
) {
    val descriptionName = if (isNagging) "안 먹은 약" else "오늘 먹은 약"
    val routineCount = if (isNagging) {
        user.notTakenCount
    } else {
        todayRoutineCount
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        YakssokImage(
            modifier = Modifier.size(52.dp),
            imageUrl = user.profileImage
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = user.relationName,
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey400
            )
            Text(
                text = user.nickName,
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey600
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.align(
                alignment = Alignment.Bottom
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = descriptionName,
                style = YakssokTheme.typography.subtitle2,
                color = YakssokTheme.color.grey500
            )

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = YakssokTheme.color.grey100,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${routineCount}개",
                    style = YakssokTheme.typography.subtitle2,
                    color = YakssokTheme.color.grey900
                )
            }
        }
    }
}

@Composable
private fun SelectFeedbackRow(
    textList: List<String>,
    isNagging: Boolean,
    selectedTextIdx: Int?,
    onTextChange: (Int?) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        textList.forEachIndexed { idx, text ->
            val isSelected = idx == selectedTextIdx
            val bgColor =
                when {
                    isNagging && isSelected -> {
                        YakssokTheme.color.primary50
                    }

                    !isNagging && isSelected -> {
                        Color(0xFFF2FAFF)
                    }

                    else -> Color.Transparent
                }
            val mainColor = if (isNagging) {
                YakssokTheme.color.primary400
            } else {
                YakssokTheme.color.subBlue
            }
            val textColor =
                if (isSelected) mainColor else YakssokTheme.color.grey950
            val borderColor =
                if (isSelected) mainColor else YakssokTheme.color.grey200

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = bgColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            if (isSelected) {
                                onTextChange(null)
                            } else {
                                onTextChange(idx)
                            }
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = YakssokTheme.typography.body2,
                    color = textColor
                )
            }
        }
    }
}