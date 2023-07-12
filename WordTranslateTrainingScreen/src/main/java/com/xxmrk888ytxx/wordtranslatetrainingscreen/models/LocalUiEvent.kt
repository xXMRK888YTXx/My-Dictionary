package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {

    data class NumberOfQuestionsChangedEvent(val newValue: String) : LocalUiEvent()

    data class ChangeIsUsePhrasesEvent(val newValue: Boolean) : LocalUiEvent()

    data class ChangeWordGroupSelectedStateEvent(val id: Int) : LocalUiEvent()

    class BackScreenEvent(val navigator: Navigator) : LocalUiEvent()

    data class ChangeAnswerTextEvent(val text: String) : LocalUiEvent()

    object StartTrainingEvent : LocalUiEvent()
}
