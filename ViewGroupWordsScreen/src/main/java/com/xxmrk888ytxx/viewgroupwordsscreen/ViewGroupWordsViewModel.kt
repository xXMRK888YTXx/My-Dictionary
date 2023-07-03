package com.xxmrk888ytxx.viewgroupwordsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordGroupInfoContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.RemoveWordContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.TextToSpeechContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState
import com.xxmrk888ytxx.viewgroupwordsscreen.models.WordOptionDialogState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewGroupWordsViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId: Int,
    private val provideWordForWordGroupContract: ProvideWordForWordGroupContract,
    private val provideWordGroupInfoContract: ProvideWordGroupInfoContract,
    private val textToSpeechContract: TextToSpeechContract,
    private val removeWordContract: RemoveWordContract
) : ViewModel(), UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when (event) {
            is LocalUiEvent.FloatButtonClickEvent -> toEditNewWordScreen(event.navigator)

            is LocalUiEvent.ClickButtonForAddNewWordOnEmptyStateEvent -> toEditNewWordScreen(event.navigator)

            is LocalUiEvent.OnBackScreenEvent -> event.navigator.backScreen()

            is LocalUiEvent.TextToSpeechEvent -> {
                textToSpeechContract.speck(event.text)
            }

            is LocalUiEvent.OpenWordForEditEvent -> toEditNewWordScreen(
                event.navigator,
                event.wordId
            )

            is LocalUiEvent.HideWordOptionDialogEvent -> {
                wordOptionDialogStateFlow.update { WordOptionDialogState.Hidden }
            }

            is LocalUiEvent.RemoveWordEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    removeWordContract.removeWord(event.wordId)
                }
            }

            is LocalUiEvent.ShowWordOptionDialogEvent -> {
                wordOptionDialogStateFlow.update { WordOptionDialogState.Showed(event.wordId) }
            }
        }
    }

    private val wordOptionDialogStateFlow: MutableStateFlow<WordOptionDialogState> =
        MutableStateFlow(WordOptionDialogState.Hidden)

    private fun toEditNewWordScreen(navigator: Navigator, editWordId: Int = 0) {
        navigator.toEditWordScreen(wordGroupId, editWordId)
    }

    override val state: Flow<ScreenState> = combine(
        provideWordForWordGroupContract.getWords(wordGroupId),
        provideWordGroupInfoContract.getWordGroupInfo(wordGroupId),
        wordOptionDialogStateFlow
    ) { wordList, wordGroupInfo,wordOptionDialogState ->
        ScreenState(wordGroupInfo, wordList,wordOptionDialogState)
    }

    private var cachedScreenState: ScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cachedScreenState


    @AssistedFactory
    interface Factory {
        fun create(wordGroupId: Int): ViewGroupWordsViewModel
    }
}