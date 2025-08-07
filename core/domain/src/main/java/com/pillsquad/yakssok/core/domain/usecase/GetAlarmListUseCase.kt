package com.pillsquad.yakssok.core.domain.usecase

import androidx.paging.PagingData
import com.pillsquad.yakssok.core.domain.repository.NotificationRepository
import com.pillsquad.yakssok.core.model.AlarmPagerItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmListUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(): Flow<PagingData<AlarmPagerItem>> = notificationRepository.getAlarmPager()
}