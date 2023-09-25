package com.xxmrk888ytxx.translatorscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent

sealed interface LocalUiEvent : UiEvent {

    object ClearTextForTranslate : LocalUiEvent

    data class TextForTranslateInput(val text:String) : LocalUiEvent
}