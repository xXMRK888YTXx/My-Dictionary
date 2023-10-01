package com.xxmrk888ytxx.translatorscreen.models

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.platform.ClipboardManager
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import kotlinx.coroutines.CoroutineScope

sealed interface LocalUiEvent : UiEvent {

    object ClearTextForTranslate : LocalUiEvent

    object AskTextForTranslateEvent : LocalUiEvent

    object AskTranslatedTextEvent : LocalUiEvent

    object ShowListForChangeOriginalLanguage : LocalUiEvent

    object ShowListForChangeLanguageForTranslate : LocalUiEvent

    data class TextForTranslateInput(val text:String) : LocalUiEvent

    class SpeechRecognizedEvent(val text: String) : LocalUiEvent

    class RequestRecognizeSpeechForTextToTranslate(
        val speechRecognizeContract: ManagedActivityResultLauncher<String, String?>,
        val snackbarHostState: SnackbarHostState,
        val context: Context,
        val uiScope:CoroutineScope
    ) : LocalUiEvent

    class PastTextFromClipboard(val text: String?) : LocalUiEvent

    class ChangeSelectedLanguage(val language: SupportedLanguage) : LocalUiEvent

    object BottomSheetDismissRequest : LocalUiEvent

    object ExchangeLanguages : LocalUiEvent

    object DismissLoadingModelsDialogStateDialog : LocalUiEvent

    object ShowFastAddWordInDictionaryBottomSheet : LocalUiEvent

    object DismissFastAddWordInDictionaryBottomSheet : LocalUiEvent

    class RequestToDownloadModelsForTranslate(
        val snackbarHostState: SnackbarHostState,
        val context: Context,
        val uiScope:CoroutineScope
    ) : LocalUiEvent

    class CopyTranslatedTextingBuffer(val clipboardManager: ClipboardManager) : LocalUiEvent
    class UpdateStateForFastAddWordInDictionaryBottomSheet(val state: FastAddWordInDictionaryBottomSheetState.Showed) :
        LocalUiEvent
}