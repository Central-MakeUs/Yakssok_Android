package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Medicine
import com.pillsquad.yakssok.core.ui.R
import java.time.LocalTime

@Composable
fun DailyMedicineList(
    medicineList: List<Medicine>,
    onItemClick: (Medicine) -> Unit,
    onNavigateToRoute: () -> Unit
) {
    val haveToTakeMedicineList = medicineList.filter { !it.isTaken }
    val takenMedicineList = medicineList.filter { it.isTaken }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(YakssokTheme.color.grey50)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "먹을 약",
                style = YakssokTheme.typography.body2,
                color = YakssokTheme.color.grey600
            )

            AddButton(onClick = onNavigateToRoute)
        }
        Spacer(modifier = Modifier.height(16.dp))
        haveToTakeMedicineList.forEach { medicine ->
            DailyMedicineRow(
                medicine = medicine,
                onClick = { onItemClick(medicine) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "복용 완료",
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
        Spacer(modifier = Modifier.height(20.dp))
        takenMedicineList.forEach { medicine ->
            DailyMedicineRow(
                medicine = medicine,
                onClick = { onItemClick(medicine) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AddButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(YakssokTheme.color.grey100),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_add),
            contentDescription = stringResource(R.string.add_mate),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
private fun DailyMedicineListPreview() {
    YakssokTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(YakssokTheme.color.grey50)
                .systemBarsPadding()
                .padding(16.dp)
        ) {
            val medicineList = listOf(
                Medicine(
                    name = "종합 비타민 오쏘몰",
                    time = LocalTime.now(),
                    isTaken = true
                ),
                Medicine(
                    name = "피부약",
                    time = LocalTime.now(),
                    isTaken = false
                ),
                Medicine(
                    name = "현대백화점에서산알약입니다이오",
                    time = LocalTime.now(),
                    isTaken = false
                )
            )

            DailyMedicineList(
                medicineList = medicineList,
                onItemClick = {},
                onNavigateToRoute = {}
            )
        }
    }
}