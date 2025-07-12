package com.pillsquad.yakssok.feature.routine

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.routine.component.DatePicker
import com.pillsquad.yakssok.feature.routine.component.TimePicker
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
internal fun RoutineRoute(
    onNavigateBack: () -> Unit
) {

    BackHandler {
        onNavigateBack
    }

    RoutineScreen()
}

@OptIn(ExperimentalTime::class)
@Composable
internal fun RoutineScreen(

) {
    var selectedTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time) }
    var selectedDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }

    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        TimePicker(
            initialTime = selectedTime,
        ) {
            selectedTime = it
        }

        Spacer(modifier = Modifier.height(40.dp))

        DatePicker(
            initialDate = selectedDate,
        ) {
            selectedDate = it
        }
    }
}
