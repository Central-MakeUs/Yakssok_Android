package com.pillsquad.yakssok.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pillsquad.yakssok.core.designsystem.component.YakssokImage
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.ui.R

@Composable
fun MateLazyRow(
    modifier: Modifier = Modifier,
    userList: List<User>,
    selectedUserIdx: Int = -1,
    imgSize: Int = 52,
    iconSize: Int = 20,
    iconButtonColor: Color = YakssokTheme.color.grey100,
    onNavigateMate: (() -> Unit)? = null,
    onMateClick: (Int) -> Unit = {}
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(userList.size) { idx ->
            val user = userList[idx]

            MateItem(
                user = user,
                imgSize = imgSize,
                onMateClick = { onMateClick(idx) },
                isClicked = (idx == selectedUserIdx)
            )
        }
        onNavigateMate?.let {
            item {
                Column {
                    AddIconButton(
                        size = imgSize,
                        iconSize = iconSize,
                        iconButtonColor = iconButtonColor,
                        onClick = onNavigateMate
                    )
                    Spacer(modifier = Modifier.height(29.dp))
                }
            }
        }
    }
}

@Composable
private fun MateItem(
    user: User,
    imgSize: Int = 52,
    onMateClick: () -> Unit,
    isClicked: Boolean
) {
    Column(
        modifier = Modifier.clickable { onMateClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YakssokImage(
            modifier = Modifier.size(imgSize.dp),
            imageUrl = user.profileImage,
            isStroke = isClicked
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.height(21.dp),
            text = user.relationName,
            style = YakssokTheme.typography.body2,
            color = YakssokTheme.color.grey600
        )
    }
}

@Composable
private fun AddIconButton(
    size: Int,
    iconSize: Int,
    iconButtonColor: Color,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(iconButtonColor),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(iconSize.dp),
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_mate),
                tint = YakssokTheme.color.grey400
            )
        }
    }
}