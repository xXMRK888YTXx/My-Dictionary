package com.xxmrk888ytxx.addwordscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.addwordscreen.models.LocalUiEvent
import com.xxmrk888ytxx.addwordscreen.models.ScreenState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class AddWordViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId: Int,
) : ViewModel(), UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.EnteredWordTextFieldInputEvent -> {
                enteredWordTextFieldState.update { event.text }
            }

            is LocalUiEvent.TranslateForEnteredWordTextFieldInputEvent -> {
                translateForEnteredWordTextFieldState.update { event.text }
            }

            is LocalUiEvent.TranscriptTextFieldInputEvent -> {
                transcriptTextFieldState.update { event.text }
            }

            LocalUiEvent.AddNewPhraseEvent -> {}
        }
    }

    private val enteredWordTextFieldState = MutableStateFlow("")

    private val translateForEnteredWordTextFieldState = MutableStateFlow("")

    private val transcriptTextFieldState = MutableStateFlow("")

    override val state: Flow<ScreenState> = combine(
        enteredWordTextFieldState,
        translateForEnteredWordTextFieldState,
        transcriptTextFieldState
    ) { enteredWordTextFieldText, translateForEnteredWordTextFieldText, transcriptTextFieldText ->

        ScreenState(
            enteredWordTextFieldText,
            translateForEnteredWordTextFieldText,
            transcriptTextFieldText
        ).also { state -> cachedScreenState = state }
    }

    private var cachedScreenState: ScreenState = ScreenState()


    override val defValue: ScreenState
        get() = cachedScreenState

    @AssistedFactory
    interface Factory {
        fun create(wordGroupId: Int): AddWordViewModel
    }
}