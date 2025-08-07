package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.ui.R

@Composable
fun NoMedicineColumn(
    modifier: Modifier,
    isNeverAlarm: Boolean,
    onNavigateToRoutine: () -> Unit
) {
    val iconResource = if (isNeverAlarm) R.drawable.img_pill else R.drawable.img_empty_medicine
    val text = if (isNeverAlarm) R.string.no_alarm else R.string.no_medicine_today

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.have_to_take),
            textAlign = TextAlign.Start,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
        Spacer(modifier = Modifier.height(20.dp))
        Icon(
            modifier = Modifier.size(86.dp),
            painter = painterResource(iconResource),
            contentDescription = stringResource(R.string.pill_image),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(text),
            textAlign = TextAlign.Center,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey900
        )
        Spacer(modifier = Modifier.height(20.dp))
        AddMedicineButton(onClick = onNavigateToRoutine)
    }
}

@Composable
private fun AddMedicineButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = YakssokTheme.color.grey200,
                shape = RoundedCornerShape(12.dp)
            )
            .background(YakssokTheme.color.grey50)
            .clickable(onClick = onClick)
            .padding(start = 12.dp, end = 6.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.add_medicine),
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_medicine),
            tint = YakssokTheme.color.grey400,
            modifier = Modifier.size(24.dp)
        )
    }
}