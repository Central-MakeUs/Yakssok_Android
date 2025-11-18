package com.pillsquad.yakssok.core.data.mock

import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.FeedbackType
import com.pillsquad.yakssok.core.model.MedicationType
import com.pillsquad.yakssok.core.model.Routine
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserCache
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class MockDataSource @Inject constructor() {
    private val mockRoutineList = listOf(
        Routine(
            routineId = 111,
            medicationName = "종합 비타민 오쏘몰",
            medicationType = MedicationType.SUPPLEMENT,
            intakeTime = LocalTime(9, 0, 0, 0),
            isTaken = false
        ),
        Routine(
            routineId = 112,
            medicationName = "종합 비타민 오쏘몰",
            medicationType = MedicationType.SUPPLEMENT,
            intakeTime = LocalTime(13, 0, 0, 0),
            isTaken = false
        )
    )

    fun getMateList(): List<User> {
        return listOf(
            User(
                id = 1,
                nickName = "김OO",
                profileImage = "",
                isNotMedicine = false
            )
        )
    }

    fun getFeedbackTarget(): List<FeedbackTarget> {
        return listOf(
            FeedbackTarget(
                userId = 1,
                nickName = "김OO",
                profileImageUrl = "",
                feedbackType = FeedbackType.NAG,
                routineCount = 2,
                routineList = mockRoutineList
            )
        )
    }

    fun getRoutine(id: Int): UserCache {
        return UserCache(
            userId = id,
            routineCache = mutableMapOf(
                LocalDate.Companion.today() to mockRoutineList
            ),
            takenCache = mutableMapOf(
                LocalDate.Companion.today() to false
            )
        )
    }
}