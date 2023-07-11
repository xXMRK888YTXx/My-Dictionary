package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent

sealed class LocalUiEvent : UiEvent {
    data class NumberOfQuestionsChangedEvent(val newValue: String) : LocalUiEvent()

    data class ChangeIsUsePhrasesEvent(val newValue: Boolean) : LocalUiEvent()
}
