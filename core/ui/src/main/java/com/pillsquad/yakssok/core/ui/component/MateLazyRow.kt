package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.Mate
import com.pillsquad.yakssok.core.ui.R

@Composable
fun MateLazyRow(
    mateList: List<Mate>,
    clickedMateId: Int,
    onNavigateMate: () -> Unit,
    onMateClick: (Mate) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(mateList.size) { idx ->
            val mate = mateList[idx]

            MateItem(
                mate = mate,
                onMateClick = onMateClick,
                isClicked = (mate.id == clickedMateId)
            )
        }
        item {
            AddIconButton(onClick = onNavigateMate)
        }
    }
}

@Composable
private fun MateItem(
    mate: Mate,
    onMateClick: (Mate) -> Unit,
    isClicked: Boolean
) {
    Column(
        modifier = Modifier
            .width(52.dp)
            .height(81.dp)
            .clickable { onMateClick(mate) }
    ) {
        YakssokImage(
            modifier = Modifier.size(52.dp),
            imageUrl = mate.profileImage,
            isStroke = isClicked
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = mate.name,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
    }
}

@Composable
private fun AddIconButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(YakssokTheme.color.grey100),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.add_mate),
            tint = Color.Unspecified
        )
    }
}