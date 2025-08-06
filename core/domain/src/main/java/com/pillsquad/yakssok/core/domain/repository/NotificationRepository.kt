package com.pillsquad.yakssok.core.domain.repository

import androidx.paging.PagingData
import com.pillsquad.yakssok.core.model.AlarmPagerItem
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun getAlarmPager(): Flow<PagingData<AlarmPagerItem>>
}