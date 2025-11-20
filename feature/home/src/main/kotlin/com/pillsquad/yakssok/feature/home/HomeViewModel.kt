package com.pillsquad.yakssok.feature.home

import android.util.SparseArray
import androidx.core.util.size
import androidx.lifecycle.viewModelScope
import com.pillsquad.yakssok.core.common.now
import com.pillsquad.yakssok.core.common.today
import com.pillsquad.yakssok.core.domain.usecase.GetFeedbackTargetUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserProfileListUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserRoutineUseCase
import com.pillsquad.yakssok.core.domain.usecase.GetUserTutorialCompleteUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostFeedbackUseCase
import com.pillsquad.yakssok.core.domain.usecase.PostUserTutorialCompleteUseCase
import com.pillsquad.yakssok.core.domain.usecase.UpdateRoutineTakenUseCase
import com.pillsquad.yakssok.core.model.FeedbackTarget
import com.pillsquad.yakssok.core.model.User
import com.pillsquad.yakssok.core.model.UserCache
import com.pillsquad.yakssok.core.ui.base.BaseViewModel
import com.pillsquad.yakssok.core.ui.model.TutorialTargetKey
import com.pillsquad.yakssok.feature.home.model.HomeIntent
import com.pillsquad.yakssok.feature.home.model.HomeSideEffect
import com.pillsquad.yakssok.feature.home.model.HomeState
import com.pillsquad.yakssok.feature.home.model.RoutineGroup
import com.pillsquad.yakssok.feature.home.model.toRoutineGroup
import com.pillsquad.yakssok.feature.home.model.toRoutineList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserProfileListUseCase: GetUserProfileListUseCase,
    private val getUserRoutineUseCase: GetUserRoutineUseCase,
    private val getFeedbackTargetUseCase: GetFeedbackTargetUseCase,
    private val updateRoutineTakenUseCase: UpdateRoutineTakenUseCase,
    private val postFeedbackUseCase: PostFeedbackUseCase,
    private val postUserTutorialCompleteUseCase: PostUserTutorialCompleteUseCase,
    getTutorialCompleteUseCase: GetUserTutorialCompleteUseCase
) : BaseViewModel<HomeIntent, HomeState, HomeSideEffect>(HomeState()) {
    private val today get() = LocalDate.today()
    private val now get() = LocalTime.now()

    init {
        viewModelScope.launch {
            getTutorialCompleteUseCase().collect { isComplete ->
                intent { copy(isTutorialComplete = isComplete) }
            }
        }

        onIntent(HomeIntent.LoadInitial)
    }

    override fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadInitial -> loadHome(initialLoad = true)
            HomeIntent.Refresh -> loadHome(initialLoad = false)
            HomeIntent.ClearRemind -> clearRemind()
            HomeIntent.CloseFBDialog -> setFeedbackTarget(fbTarget = null)
            HomeIntent.NextTutorialStep -> nextTutorialStep()
            HomeIntent.NavigateToAlert -> postSideEffect(HomeSideEffect.NavigateToAlert)
            HomeIntent.NavigateToCalendar -> postSideEffect(HomeSideEffect.NavigateToCalendar)
            HomeIntent.NavigateToMate -> postSideEffect(HomeSideEffect.NavigateToMate)
            HomeIntent.NavigateToMyPage -> postSideEffect(HomeSideEffect.NavigateToMyPage)
            HomeIntent.NavigateToRoutine -> postSideEffect(HomeSideEffect.NavigateToRoutine)
            is HomeIntent.SelectDate -> selectDate(date = intent.date)
            is HomeIntent.SelectUser -> selectUser(idx = intent.idx)
            is HomeIntent.ToggleRoutine -> toggleRoutine(routineId = intent.routineId)
            is HomeIntent.PostFeedback -> postFeedback(intentData = intent)
            is HomeIntent.SetFeedbackDialog -> setFeedbackTarget(fbTarget = intent.feedbackTarget)
        }
    }

    private fun loadHome(initialLoad: Boolean) {
        viewModelScope.launch {
            intent { copy(isLoading = initialLoad, isRefreshing = !initialLoad) }

            val previous = currentState

            supervisorScope {
                val users = getUserProfileListUseCase()
                    .getOrElse { throwable ->
                        postSideEffect(HomeSideEffect.ShowError(throwable = throwable))
                        previous.userList
                    }

                val (start, end) = getStartEndDate()

                val routineDef = async { buildRoutineCache(users, start, end) }
                val targetsDef = async {
                    getFeedbackTargetUseCase()
                        .getOrElse { throwable ->
                            postSideEffect(HomeSideEffect.ShowError(throwable = throwable))
                            emptyList()
                        }
                }

                val cache = routineDef.await()
                val targets = targetsDef.await()
                val adjustedUsers = recomputeIsNotMedicine(users, cache)

                val todayRemind =
                    cache[0]?.get(today)?.haveToTake?.filter { it.isFeedbackRoutine(now) }.orEmpty()

                val shouldShowRemind = (previous.isInit) && todayRemind.isNotEmpty()

                intent {
                    copy(
                        isLoading = false,
                        isRefreshing = false,
                        isInit = false,
                        selectedDate = previous.selectedDate.takeIf { !initialLoad } ?: today,
                        selectedUserIdx = previous.selectedUserIdx,
                        userList = adjustedUsers,
                        feedbackTargetList = targets,
                        routineCache = cache,
                        remindList = if (shouldShowRemind) todayRemind else (previous.remindList)
                    )
                }
            }
        }
    }


    private fun selectDate(date: LocalDate) {
        intent { copy(selectedDate = date) }
    }

    private fun selectUser(idx: Int) {
        intent { copy(selectedUserIdx = idx) }
    }

    private fun toggleRoutine(routineId: Int) {
        val state = currentState
        val userIdx = state.selectedUserIdx
        val date = state.selectedDate
        if (userIdx != 0 || date != today) return

        val currentMap = state.routineCache[userIdx] ?: return
        val updated = currentMap[date]?.toRoutineList()?.map {
            if (it.routineId == routineId) it.copy(isTaken = !it.isTaken) else it
        } ?: return

        val newUserMap = currentMap.toMutableMap().apply {
            put(date, updated.toRoutineGroup())
        }
        val newCache = state.routineCache.copyAndPut(userIdx, newUserMap)

        intent { copy(routineCache = newCache) }

        viewModelScope.launch {
            updateRoutineTakenUseCase(routineId)
                .onFailure { throwable ->
                    postSideEffect(HomeSideEffect.ShowError(throwable))
                }
        }
    }

    fun postFeedback(intentData: HomeIntent.PostFeedback) {
        viewModelScope.launch {
            postFeedbackUseCase(intentData.userId, intentData.message, intentData.type)
                .onSuccess {
                    val newList = currentState.feedbackTargetList
                        .filter { it.userId != intentData.userId }

                    intent { copy(feedbackTargetList = newList) }
                }
                .onFailure { throwable ->
                    postSideEffect(HomeSideEffect.ShowError(throwable))
                }

            setFeedbackTarget(null)
        }
    }

    private fun clearRemind() {
        intent { copy(remindList = emptyList()) }
    }

    // 피드백 다이얼로그 타겟 설정 및 삭제
    private fun setFeedbackTarget(fbTarget: FeedbackTarget?) {
        intent { copy(feedbackDialogTarget = fbTarget) }
    }

    private fun nextTutorialStep() {
        val newStep = currentState.tutorialStep + 1
        intent { copy(tutorialStep = newStep) }
        updateTutorialTarget(newStep)
    }

    private fun updateTutorialTarget(step: Int) {
        val key = when (step) {
            0 -> TutorialTargetKey.ADD_FRIEND
            1 -> TutorialTargetKey.ADD_ROUTINE
            2 -> TutorialTargetKey.FEEDBACK_ITEM
            3 -> {
                val feedbackTarget = currentState.feedbackTargetList[0]
                setFeedbackTarget(feedbackTarget)
                TutorialTargetKey.EMPTY
            }
            4 -> TutorialTargetKey.FEEDBACK_BUTTON
            5 -> {
                setFeedbackTarget(null)
                TutorialTargetKey.NOTIFICATION
                // delay -> 변경
            }
            6 -> TutorialTargetKey.NOTIFICATION_COMPLETE
            7 -> {
                endTutorial()
                TutorialTargetKey.END
            }
            else -> {
                TutorialTargetKey.EMPTY
            }
        }

        intent { copy(currentTargetKey = key) }
    }

    private fun endTutorial() {
        viewModelScope.launch {
            postUserTutorialCompleteUseCase()
                .onSuccess {
                    onIntent(HomeIntent.LoadInitial)
                }
                .onFailure {
                    intent { copy(isTutorialComplete = false) }
                    onIntent(HomeIntent.LoadInitial)
                }
        }
    }

    private suspend fun buildRoutineCache(
        users: List<User>,
        startDate: LocalDate,
        endDate: LocalDate
    ): SparseArray<MutableMap<LocalDate, RoutineGroup>> {
        var acc = SparseArray<MutableMap<LocalDate, RoutineGroup>>()

        val my = fetchRoutineOrEmpty(userId = null, startDate, endDate)
            .routineCache.mapValues { (_, list) -> list.toRoutineGroup() }.toMutableMap()
        acc = acc.copyAndPut(0, my)

        users.drop(1).forEach { friend ->
            val cache = fetchRoutineOrEmpty(friend.id, startDate, endDate)
                .routineCache.mapValues { (_, list) -> list.toRoutineGroup() }.toMutableMap()
            val userIdx = users.indexOfFirst { it.id == friend.id }
            if (userIdx != -1) acc = acc.copyAndPut(userIdx, cache)
        }
        return acc
    }

    private suspend fun fetchRoutineOrEmpty(
        userId: Int?,
        startDate: LocalDate,
        endDate: LocalDate
    ): UserCache {
        return if (userId == null) {
            getUserRoutineUseCase(startDate, endDate)
                .getOrElse { throwable ->
                    postSideEffect(HomeSideEffect.ShowError(throwable = throwable))
                    UserCache.empty()
                }
        } else {
            getUserRoutineUseCase(userId, startDate, endDate)
                .getOrElse { throwable ->
                    postSideEffect(HomeSideEffect.ShowError(throwable = throwable))
                    UserCache.empty()
                }
        }
    }

    private fun recomputeIsNotMedicine(
        users: List<User>,
        cache: SparseArray<MutableMap<LocalDate, RoutineGroup>>
    ): List<User> {
        return users.mapIndexed { idx, user ->
            val hasAny = cache[idx]?.values?.any {
                it.haveToTake.isNotEmpty() || it.taken.isNotEmpty()
            } == true
            user.copy(isNotMedicine = !hasAny)
        }
    }

    private fun getStartEndDate(): Pair<LocalDate, LocalDate> {
        val currentDayOfWeek = today.dayOfWeek.isoDayNumber
        val monday = today.minus(currentDayOfWeek - 1, DateTimeUnit.DAY)
        val sunday = monday.plus(6, DateTimeUnit.DAY)
        return monday to sunday
    }

    private fun <V> SparseArray<V>.copyAndPut(key: Int, value: V): SparseArray<V> {
        return SparseArray<V>(this.size + 1).also { newArr ->
            for (i in 0 until this.size) newArr.put(this.keyAt(i), this.valueAt(i))
            newArr.put(key, value)
        }
    }
}