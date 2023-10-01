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
import com.xxmrk888ytxx.viewgroupwordsscreen.models.SearchState
import com.xxmrk888ytxx.viewgroupwordsscreen.models.Word
import com.xxmrk888ytxx.viewgroupwordsscreen.models.WordOptionDialogState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
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
                viewModelScope.launch(Dispatchers.Default) {
                    textToSpeechContract.speck(event.text)
                }
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

            is LocalUiEvent.ChangeSearchStateEvent -> {
                searchStateFlow.update { if(it is SearchState.Enabled) SearchState.Disabled else SearchState.Enabled() }
            }

            is LocalUiEvent.OnChangeSearchValueEvent -> {
                searchStateFlow.update { if(it is SearchState.Enabled) it.copy(event.value) else it }
            }
        }
    }

    private val wordOptionDialogStateFlow: MutableStateFlow<WordOptionDialogState> =
        MutableStateFlow(WordOptionDialogState.Hidden)

    private val searchStateFlow:MutableStateFlow<SearchState> = MutableStateFlow(SearchState.Disabled)

    private val words = combine(
        provideWordForWordGroupContract.getWords(wordGroupId),
        searchStateFlow
    ) { wordsList,searchState ->
        val list = if(searchState is SearchState.Disabled) wordsList else wordsList.filter {
            isValidForSearch(it,searchState)
        }

        list.reversed().toImmutableList()
    }

    private suspend fun isValidForSearch(word: Word, searchState: SearchState) : Boolean {
        val searchText = (searchState as? SearchState.Enabled)?.searchValue ?: return true

        return word.wordText.contains(searchText,true)
                || word.translateText.contains(searchText,true)
    }

    private fun toEditNewWordScreen(navigator: Navigator, editWordId: Int = 0) {
        navigator.toEditWordScreen(wordGroupId, editWordId)
    }

    override val state: Flow<ScreenState> = combine(
        words,
        provideWordGroupInfoContract.getWordGroupInfo(wordGroupId),
        wordOptionDialogStateFlow,
        searchStateFlow
    ) { wordList, wordGroupInfo,wordOptionDialogState,searchState ->
        ScreenState(wordGroupInfo, wordList,wordOptionDialogState,searchState)
    }

    private var cachedScreenState: ScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cachedScreenState


    @AssistedFactory
    interface Factory {
        fun create(wordGroupId: Int): ViewGroupWordsViewModel
    }
}