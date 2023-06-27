package com.xxmrk888ytxx.addwordscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent

internal sealed class LocalUiEvent : UiEvent {
    object AddNewPhraseEvent : LocalUiEvent()

    data class EnteredWordTextFieldInputEvent(val text:String) : LocalUiEvent()

    data class TranslateForEnteredWordTextFieldInputEvent(val text: String) : LocalUiEvent()

    data class TranscriptTextFieldInputEvent(val text: String) : LocalUiEvent()

}
