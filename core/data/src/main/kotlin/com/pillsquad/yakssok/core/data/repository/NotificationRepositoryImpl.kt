package com.pillsquad.yakssok.core.data.repository

import androidx.paging.PagingData
import com.pillsquad.yakssok.core.data.PagingDataSource
import com.pillsquad.yakssok.core.data.mapper.toAlarmPagerItem
import com.pillsquad.yakssok.core.data.mapper.toResult
import com.pillsquad.yakssok.core.domain.repository.NotificationRepository
import com.pillsquad.yakssok.core.model.AlarmPagerItem
import com.pillsquad.yakssok.core.network.datasource.NotificationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override fun getAlarmPager(): Flow<PagingData<AlarmPagerItem>> {
        return PagingDataSource.createPager(
            pageSize = 20,
            keySelector = { it.notificationId.toString() },
            fetcher = { startKey, perPage ->
                notificationDataSource.fetchAlarms(startKey, perPage).toResult(
                    transform = { it.content.map { alarm -> alarm.toAlarmPagerItem() } }
                )
            }
        )
    }
}