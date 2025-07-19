package com.pillsquad.yakssok.feature.myroutine.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokIconButton
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.PillProgressType
import com.pillsquad.yakssok.core.model.PillType
import com.pillsquad.yakssok.core.model.WeekType
import com.pillsquad.yakssok.core.ui.component.PillTypeCard
import com.pillsquad.yakssok.core.ui.component.WeekRow
import com.pillsquad.yakssok.feature.myroutine.model.MyRoutineUiModel

@Composable
internal fun InfoCard(
    uiModel: MyRoutineUiModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = YakssokTheme.color.grey50,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        TopColumn(
            pillType = uiModel.pillType,
            pillName = uiModel.pillName,
            intakeDays = uiModel.intakeDays,
            progressType = uiModel.pillProgressType,
            onMenuClick = {}
        )

        HorizontalDivider(thickness = 1.dp, color = YakssokTheme.color.grey100)

        BottomColumn(
            intakeCount = uiModel.intakeCount,
            intakeTimes = uiModel.intakeTimes
        )
    }
}

@Composable
private fun TopColumn(
    pillType: PillType,
    progressType: PillProgressType,
    pillName: String,
    intakeDays: List<WeekType>,
    onMenuClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            PillTypeCard(pillType)
            Spacer(modifier = Modifier.width(8.dp))
            PillProgressTypeCard(progressType)
            Spacer(modifier = Modifier.weight(1f))
            YakssokIconButton(
                imageVector = Icons.Default.MoreVert,
                color = YakssokTheme.color.grey400,
                onClick = onMenuClick
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pillName,
            style = YakssokTheme.typography.subtitle1,
            color = YakssokTheme.color.grey950
        )

        Spacer(modifier = Modifier.height(8.dp))

        WeekRow(intakeDays)
    }
}

@Composable
private fun BottomColumn(
    intakeCount: Int,
    intakeTimes: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "count icon",
                tint = YakssokTheme.color.grey600
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "하루에 ${intakeCount}번",
                style = YakssokTheme.typography.body1,
                color = YakssokTheme.color.grey600
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = intakeTimes,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
    }
}