package com.pillsquad.yakssok.core.ui.component


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.ui.R
import com.pillsquad.yakssok.core.ui.ext.customInsets
import kotlinx.datetime.LocalTime

fun LazyListScope.dailyMedicineList(
    isCheckBoxVisible: Boolean = false,
    haveToTake: List<Routine>,
    taken: List<Routine>,
    onItemClick: (Int) -> Unit,
    onNavigateToRoute: () -> Unit
) {
    val rows = buildMedRows(haveToTake, taken) { it.intakeTime }

    NestedScrollConnection

    item {
        TitleRow(
            title = stringResource(R.string.have_to_take),
            onNavigateToRoute = onNavigateToRoute
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(YakssokTheme.color.grey50)
        )
    }

    items(haveToTake, key = { it.routineId ?: it.hashCode() }) { medicine ->
        MedicineRowItem(
            routine = medicine,
            isCheckBoxVisible = isCheckBoxVisible,
            onMoveRequest = { medicine.routineId?.let { onItemClick(it) } }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    item {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(YakssokTheme.color.grey50)
        )
        TitleRow(stringResource(R.string.taked_medicine))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(YakssokTheme.color.grey50)
        )
    }

    items(
        items = taken,
        key = { it.routineId ?: it.hashCode() }
    ) { medicine ->
        MedicineRowItem(
            routine = medicine,
            isCheckBoxVisible = isCheckBoxVisible,
            onMoveRequest = { medicine.routineId?.let { onItemClick(it) } }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.MedicineRowItem(
    routine: Routine,
    isCheckBoxVisible: Boolean,
    onMoveRequest: (Routine) -> Unit
) {
    var stage by rememberSaveable { mutableIntStateOf(0) } // 0=정상, 1=축소중, 2=확대중
    val scale by animateFloatAsState(
        targetValue = when (stage) {
            1 -> 0.8f   // 축소
            2 -> 1.05f  // 팅! 커짐
            else -> 1f
        },
        animationSpec = tween(if (stage == 1) 120 else 160),
        finishedListener = {
            if (stage == 1) {
                // 축소 끝 → 리스트 이동
                onMoveRequest(routine)
                stage = 2
            } else if (stage == 2) {
                // 팅! 끝 → 원래 크기로 복귀
                stage = 0
            }
        }
    )

    DailyMedicineRow(
        modifier = Modifier
            .animateItem()
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .padding(horizontal = 16.dp),
        isCheckBoxVisible = isCheckBoxVisible,
        routine = routine,
        onClick = { if (stage == 0) stage = 1 } // 축소 애니메이션 시작
    )
}

@Composable
private fun TitleRow(
    title: String,
    onNavigateToRoute: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(YakssokTheme.color.grey50)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )

        onNavigateToRoute?.let {
            AddButton(onClick = it)
        }
    }
}

@Composable
private fun AddButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(YakssokTheme.color.grey100)
            .size(28.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_mate),
            tint = Color.Unspecified
        )
    }
}

private sealed interface MedRow {
    data class Header(val title: String, val id: String) : MedRow
    data class Entry(val routine: Routine) : MedRow
}

private fun buildMedRows(
    haveToTake: List<Routine>,
    taken: List<Routine>,
    timeKey: LocalTime
): List<MedRow> {
    val rows = mutableListOf<MedRow>()
    rows += MedRow.Header("먹을 약", "HEADER_NOT_TAKEN")
    rows += haveToTake.sortedBy(timeKey).map { MedRow.Entry(it) }
    rows += MedRow.Header("복용 완료", "HEADER_TAKEN")
    rows += taken.sortedBy(timeKey).map { MedRow.Entry(it) }
    return rows
}

@Preview
@Composable
private fun DailyMedicineListPreview() {
    YakssokTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(YakssokTheme.color.grey50)
                .customInsets(top = true, bottom = true)
                .padding(16.dp)
        ) {
            dailyMedicineList(
                haveToTake = listOf(),
                taken = listOf(),
                onItemClick = {},
                onNavigateToRoute = {}
            )
        }
    }
}