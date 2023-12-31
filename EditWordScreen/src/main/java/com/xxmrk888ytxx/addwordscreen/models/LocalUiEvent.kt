package com.xxmrk888ytxx.addwordscreen.models

import androidx.compose.material3.SnackbarHostState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

internal sealed class LocalUiEvent : UiEvent {
    object AddNewPhraseEvent : LocalUiEvent()
    class WordInfoEnterCompleted(
        val navigator: Navigator,
        val snackbarHostState: SnackbarHostState,
        val snackText:String
        ) : LocalUiEvent()

    data class EnteredWordTextFieldInputEvent(val text:String) : LocalUiEvent()

    data class TranslateForEnteredWordTextFieldInputEvent(val text: String) : LocalUiEvent()

    data class TranscriptTextFieldInputEvent(val text: String) : LocalUiEvent()
    data class UpdateOriginalPhraseEvent(val localId: Int, val text: String) : LocalUiEvent()

    data class UpdateTranslatePhraseEvent(val localId: Int, val text: String) : LocalUiEvent()
    data class RemovePhraseEvent(val localPhraseId: Int) : LocalUiEvent()
}
