@file:OptIn(ExperimentalFoundationApi::class)

package com.xxmrk888ytxx.featureviewscreen.models

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

sealed class LocalUiEvent : UiEvent {
    object OpenPrivacyPolicy : LocalUiEvent()

    object OpenTermsOfUse : LocalUiEvent()

    class ToWordScreenStateEvent(
        val pagerState: PagerState,
        val uiScope: CoroutineScope
    ) : LocalUiEvent()

    class ToTrainingScreenStateEvent(
        val pagerState: PagerState,
        val uiScope: CoroutineScope
    ) : LocalUiEvent()

    class ToFreeScreenStateEvent(
        val pagerState: PagerState,
        val uiScope: CoroutineScope
    ) : LocalUiEvent()

    class ToAgreeWithRulesScreenStateEvent(
        val pagerState: PagerState,
        val uiScope: CoroutineScope
    ) : LocalUiEvent()

    class ToMainScreenEvent(val navigator: Navigator) : LocalUiEvent()

    class ChangeAgreeWithPrivacyPolicyState(val newState:Boolean) : LocalUiEvent()

    class ChangeAgreeWithTermsOfUseState(val newState:Boolean) : LocalUiEvent()

    class SkipEvent(val pager: PagerState, val uiScope: CoroutineScope) : LocalUiEvent()
}