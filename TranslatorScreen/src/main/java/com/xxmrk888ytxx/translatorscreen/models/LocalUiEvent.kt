package com.xxmrk888ytxx.translatorscreen.models

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.text.AnnotatedString
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import kotlinx.coroutines.CoroutineScope

sealed interface LocalUiEvent : UiEvent {

    object ClearTextForTranslate : LocalUiEvent

    object AskTestEvent : LocalUiEvent

    data class TextForTranslateInput(val text:String) : LocalUiEvent

    class SpeechRecognizedEvent(val text: String) : LocalUiEvent

    class RequestRecognizeSpeech(
        val speechRecognizeContract: ManagedActivityResultLauncher<String, String?>,
        val snackbarHostState: SnackbarHostState,
        val context: Context,
        val uiScope:CoroutineScope
    ) : LocalUiEvent

    class PastTextFromClipboard(val text: String?) : LocalUiEvent
}