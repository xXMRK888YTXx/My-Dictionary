package com.xxmrk888ytxx.addwordscreen

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.addwordscreen.contracts.ProvideWordInfoContract
import com.xxmrk888ytxx.addwordscreen.contracts.ProvideWordPhrasesContract
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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditWordViewModel @AssistedInject constructor(
    @Assisted("wordGroupId") private val wordGroupId: Int,
    @Assisted("editWordId") private val editWordId:Int,
    private val logger: Logger,
    private val saveWordContract: SaveWordContract,
    private val saveWordPhraseContract: SaveWordPhraseContract,
    private val provideWordInfoContract: ProvideWordInfoContract,
    private val provideWordPhrasesContract: ProvideWordPhrasesContract
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
                phrasesHolder.addPhrases()
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
                        editWordId,
                        wordGroupId,
                        state.enteredWordTextFieldText,
                        state.translateForEnteredWordTextFieldText,
                        state.transcriptTextFieldText
                    )

                    state.phrasesList.forEach {
                        saveWordPhraseContract.savePhrase(
                            wordId,
                            it.id,
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


    private suspend fun loadWord(wordId:Int) {
        if(wordId == 0) return

        val wordDeferred = viewModelScope.async(Dispatchers.IO) {
            provideWordInfoContract.getWordInfo(editWordId)
        }

        val phrasesDeferred = viewModelScope.async(Dispatchers.IO) {
            provideWordPhrasesContract.getPhrases(editWordId)
        }

        val word = wordDeferred.await()

        enteredWordTextFieldState.update { word.wordText }
        translateForEnteredWordTextFieldState.update { word.translateText }
        transcriptTextFieldState.update { word.transcriptionText }

        phrasesDeferred.await().forEach {
            phrasesHolder.addPhrases(it)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            loadWord(editWordId)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("wordGroupId") wordGroupId: Int,
            @Assisted("editWordId") editWordId:Int,
        ): EditWordViewModel
    }
}