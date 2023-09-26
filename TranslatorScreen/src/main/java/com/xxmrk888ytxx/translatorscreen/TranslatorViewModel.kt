package com.xxmrk888ytxx.translatorscreen

import android.content.ActivityNotFoundException
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.getWithCast
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TranslatorViewModel @Inject constructor(

) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.TextForTranslateInput -> {
                textForTranslate.update { event.text }
            }

            LocalUiEvent.ClearTextForTranslate -> { updateTextForTranslateAndTranslate { "" } }

            LocalUiEvent.AskTestEvent -> {

            }

            is LocalUiEvent.RequestRecognizeSpeech -> {
                try {
                    event.speechRecognizeContract.launch("en")
                }catch (e:ActivityNotFoundException) {
                    event.uiScope.launch {
                        event.snackbarHostState.showSnackbar(
                            event.context.getString(R.string.could_not_found_app_for_recognize_speech)
                        )
                    }
                }
            }

            is LocalUiEvent.SpeechRecognizedEvent -> {
                updateTextForTranslateAndTranslate { it + event.text }
            }

            is LocalUiEvent.PastTextFromClipboard -> {
                val text = event.text ?: return

                updateTextForTranslateAndTranslate { it + text }
            }
        }
    }

    private fun updateTextForTranslateAndTranslate(
        onUpdate:(String) -> String
    ) {
        textForTranslate.update(onUpdate)
    }

    private val textForTranslate = MutableStateFlow("")

    override val state: Flow<ScreenState> = combine(textForTranslate) { flowArray:Array<Any> ->
        ScreenState(
            textForState = flowArray.getWithCast(0)
        ).also {
            cashedState = it
        }
    }

    private var cashedState = ScreenState()

    override val defValue: ScreenState
        get() = cashedState
}