package com.pillsquad.yakssok.core.ui.model

import androidx.annotation.StringRes
import com.pillsquad.yakssok.core.ui.R

enum class TutorialTargetKey(val loc: Location, val textGroupRes: ExplainTextGroupRes) {
    ADD_FRIEND(
        loc = Location.TOP_START,
        textGroupRes = ExplainTextGroupRes(
            firstContent = R.string.add_friend_first,
            highlightContent = R.string.add_friend_highlight,
            secondContent = R.string.add_friend_second
        )
    ),
    ADD_ROUTINE(
        loc = Location.BOTTOM_END,
        textGroupRes = ExplainTextGroupRes(
            R.string.add_routine_first,
            R.string.add_routine_highlight,
            R.string.add_routine_second
        )
    ),
    FEEDBACK_ITEM(
        loc = Location.TOP_START,
        textGroupRes = ExplainTextGroupRes(
            R.string.feedback_item_first,
            R.string.feedback_item_highlight,
            R.string.feedback_item_second
        )
    ),
    FEEDBACK_BUTTON(
        loc = Location.BOTTOM_END,
        textGroupRes = ExplainTextGroupRes(
            R.string.empty,
            R.string.feedback_button_highlight,
            R.string.feedback_button_second
        )
    ),
    NOTIFICATION(
        loc = Location.TOP_CENTER,
        textGroupRes = ExplainTextGroupRes(
            R.string.empty,
            R.string.empty,
            R.string.empty
        )
    ),
    NOTIFICATION_COMPLETE(
        loc = Location.TOP_CENTER,
        textGroupRes = ExplainTextGroupRes(
            R.string.notification_first,
            R.string.notification_highlight,
            R.string.notification_second
        )
    ),
    EMPTY(
        loc = Location.TOP_START,
        textGroupRes = ExplainTextGroupRes(
            R.string.empty,
            R.string.empty,
            R.string.empty
        )
    ),
    END(
        loc = Location.TOP_START,
        textGroupRes = ExplainTextGroupRes(
            R.string.empty,
            R.string.empty,
            R.string.empty
        )
    )
}

enum class Location {
    TOP_START, TOP_END, TOP_CENTER, BOTTOM_START, BOTTOM_END, BOTTOM_CENTER
}

data class ExplainTextGroupRes(
    @param:StringRes val firstContent: Int,
    @param:StringRes val highlightContent: Int,
    @param:StringRes val secondContent: Int
)