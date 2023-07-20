@file:OptIn(ExperimentalFoundationApi::class)

package com.xxmrk888ytxx.wordbyeartrainingscreen.models

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

sealed class LocalUiEvent : UiEvent {

    object StartTrainingEvent : LocalUiEvent()

    object CheckAnswer : LocalUiEvent()

    data class NumberOfQuestionsChangedEvent(val newValue: String) : LocalUiEvent()

    data class ChangeIsUsePhrasesEvent(val newValue: Boolean) : LocalUiEvent()

    data class ChangeWordGroupSelectedStateEvent(val groupWordId: Int) : LocalUiEvent()

    class BackScreenEvent(val navigator: Navigator) : LocalUiEvent()

    data class ChangeAnswerTextEvent(val newValue: String) : LocalUiEvent()

    data class PlayQuestionWordEvent(val questionIndex: Int) : LocalUiEvent()

    class NextQuestion(val pager: PagerState,val scope: CoroutineScope) : LocalUiEvent()
}
