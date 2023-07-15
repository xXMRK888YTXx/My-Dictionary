package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

sealed class LocalUiEvent : UiEvent {

    data class NumberOfQuestionsChangedEvent(val newValue: String) : LocalUiEvent()

    data class ChangeIsUsePhrasesEvent(val newValue: Boolean) : LocalUiEvent()

    data class ChangeWordGroupSelectedStateEvent(val id: Int) : LocalUiEvent()

    class BackScreenEvent(val navigator: Navigator) : LocalUiEvent()

    data class ChangeAnswerTextEvent(val text: String) : LocalUiEvent()
    class NextQuestion @OptIn(ExperimentalFoundationApi::class) constructor(
        val pager: PagerState,
        val scope: CoroutineScope
    ) : LocalUiEvent()

    object StartTrainingEvent : LocalUiEvent()
}
