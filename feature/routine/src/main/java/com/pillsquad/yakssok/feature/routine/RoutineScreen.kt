package com.pillsquad.yakssok.feature.routine

import android.util.Log
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)

@Composable
internal fun RoutineRoute(
    onNavigateBack: () -> Unit
) {
    val initialTime by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
        )
    }
    val initialDate by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
    }

    RoutineScreen(
        initialTime = initialTime,
        initialDate = initialDate
    )
}

@Composable
internal fun RoutineScreen(
    initialTime: LocalTime,
    initialDate: LocalDate
) {
    var selectedTime by remember { mutableStateOf(initialTime) }
    var selectedDate by remember { mutableStateOf(initialDate) }

    Log.e("DatePicker", "selectedTime: $selectedTime")
    Log.e("DatePicker", "selectedDate: $selectedDate")

    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        TimePicker(
            initialTime = initialTime,
        ) {
            Log.e("PickerInit", "onValueChange time called with: $it")
            selectedTime = it
        }

        Spacer(modifier = Modifier.height(40.dp))

        DatePicker(
            initialDate = initialDate,
        ) {
            Log.e("PickerInit", "onValueChange date called with: $it")
            selectedDate = it
        }
    }
}
