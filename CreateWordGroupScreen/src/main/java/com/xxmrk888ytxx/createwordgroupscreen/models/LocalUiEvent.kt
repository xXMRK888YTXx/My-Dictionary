@file:OptIn(ExperimentalFoundationApi::class)

package com.xxmrk888ytxx.createwordgroupscreen.models

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import kotlinx.coroutines.CoroutineScope

internal sealed class LocalUiEvent : UiEvent {

    class TitleTextChangedEvent(val text: String) : LocalUiEvent()

    class WordGroupNameInputCompletedEvent(val pagerState: PagerState,val uiScope:CoroutineScope) : LocalUiEvent()

    data class SelectNewPrimaryLanguageEvent(val language: Language) : LocalUiEvent()

    data class SelectNewSecondaryLanguageEvent(val language: Language) : LocalUiEvent()

    class LanguageSelectCompletedEvent(val pagerState: PagerState, val uiScope: CoroutineScope) : LocalUiEvent()

}