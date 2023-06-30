package com.xxmrk888ytxx.addwordscreen

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordContract
import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordPhraseContract
import com.xxmrk888ytxx.addwordscreen.models.LocalUiEvent
import com.xxmrk888ytxx.addwordscreen.models.PhrasesHolder
import com.xxmrk888ytxx.addwordscreen.models.ScreenState
import com.xxmrk888ytxx.coreandroid.ApplicationScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddWordViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId: Int,
    private val logger: Logger,
    private val saveWordContract: SaveWordContract,
    private val saveWordPhraseContract: SaveWordPhraseContract,
) : ViewModel(), UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when (event) {
            is LocalUiEvent.EnteredWordTextFieldInputEvent -> {
                enteredWordTextFieldState.update { event.text }
            }

            is LocalUiEvent.TranslateForEnteredWordTextFieldInputEvent -> {
                translateForEnteredWordTextFieldState.update { event.text }
            }

            is LocalUiEvent.TranscriptTextFieldInputEvent -> {
                transcriptTextFieldState.update { event.text }
            }

            LocalUiEvent.AddNewPhraseEvent -> {
                phrasesHolder.addPhrases(0)
            }

            is LocalUiEvent.UpdateOriginalPhraseEvent -> {
                phrasesHolder.updatePhrases(event.localId) {
                    it.copy(phrasesText = event.text)
                }
            }

            is LocalUiEvent.UpdateTranslatePhraseEvent -> {
                phrasesHolder.updatePhrases(event.localId) {
                    it.copy(phrasesTranslate = event.text)
                }
            }

            is LocalUiEvent.RemovePhraseEvent -> {
                phrasesHolder.removePhrases(event.localPhraseId)
            }

            is LocalUiEvent.WordInfoEnterCompleted -> {
                if(isSaveWordInProcessState.value) return

                isSaveWordInProcessState.update { true }

                viewModelScope.launch(Dispatchers.IO) {

                    val state = state.first()

                    val wordId = saveWordContract.saveWord(
                        wordGroupId,
                        state.enteredWordTextFieldText,
                        state.translateForEnteredWordTextFieldText,
                        state.transcriptTextFieldText
                    )

                    state.phrasesList.forEach {
                        saveWordPhraseContract.savePhrase(
                            wordId,
                            it.phrasesText,
                            it.phrasesTranslate
                        )
                    }
                    isSaveWordInProcessState.update { false }

                    ApplicationScope.launch {
                        event.snackbarHostState.showSnackbar(
                            event.snackText,
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }

                    event.navigator.backScreen()
                }
            }
        }
    }

    private val enteredWordTextFieldState = MutableStateFlow("")

    private val translateForEnteredWordTextFieldState = MutableStateFlow("")

    private val transcriptTextFieldState = MutableStateFlow("")

    private val phrasesHolder = PhrasesHolder(logger)

    private val isSaveWordInProcessState = MutableStateFlow(false)

    override val state: Flow<ScreenState> = combine(
        enteredWordTextFieldState,
        translateForEnteredWordTextFieldState,
        transcriptTextFieldState,
        phrasesHolder.phrasesModel,
        isSaveWordInProcessState
    ) { enteredWordTextFieldText, translateForEnteredWordTextFieldText, transcriptTextFieldText, phrasesList, isSaveWordInProcess ->

        ScreenState(
            enteredWordTextFieldText,
            translateForEnteredWordTextFieldText,
            transcriptTextFieldText,
            phrasesList,
            isSaveWordInProcess
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