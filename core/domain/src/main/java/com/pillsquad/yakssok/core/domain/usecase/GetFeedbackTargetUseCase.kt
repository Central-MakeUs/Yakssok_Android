package com.pillsquad.yakssok.core.domain.usecase

import com.pillsquad.yakssok.core.domain.repository.FriendRepository
import com.pillsquad.yakssok.core.model.FeedbackTarget
import javax.inject.Inject

class GetFeedbackTargetUseCase @Inject constructor(
    private val friendRepository: FriendRepository
) {
    suspend operator fun invoke(): Result<List<FeedbackTarget>> {
        return friendRepository.getFeedbackTargetList()
    }
}