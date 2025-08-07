package com.pillsquad.yakssok.feature.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.pillsquad.yakssok.core.designsystem.component.YakssokTopAppBar
import com.pillsquad.yakssok.core.designsystem.theme.YakssokTheme
import com.pillsquad.yakssok.core.model.AlarmPagerItem
import com.pillsquad.yakssok.core.ui.ext.yakssokDefault
import com.pillsquad.yakssok.feature.alert.component.AlarmComponent
import com.pillsquad.yakssok.feature.alert.component.PageItemFooter

@Composable
internal fun AlertRoute(
    viewModel: AlertViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val lazyPagingItems = viewModel.alarmList.collectAsLazyPagingItems()

    AlertScreen(
        pagingItems = lazyPagingItems,
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun AlertScreen(
    pagingItems: LazyPagingItems<AlarmPagerItem>,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.yakssokDefault(YakssokTheme.color.grey100)
    ) {
        YakssokTopAppBar(
            title = "알림",
            onBackClick = onNavigateBack
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            reverseLayout = true
        ) {
            items(pagingItems.itemCount) { idx ->
                pagingItems[idx]?.let {
                    val name = if (it.isSentByMe) "${it.receiverNickName}에게 전송" else it.senderNickName
                    val imgUrl = if (it.isSentByMe) it.receiverProfileUrl else it.senderProfileUrl

                    AlarmComponent(
                        name = name,
                        imgUrl = imgUrl,
                        message = it.content,
                        isMine = it.isSentByMe,
                        messageType = it.notificationType,
                        time = it.createdAt
                    )
                }
            }

            if (pagingItems.loadState.append !is LoadState.NotLoading) {
                item(key = "") {
                    PageItemFooter(loadState = pagingItems.loadState.append) {
                        pagingItems.retry()
                    }
                }
            }
        }
    }
}