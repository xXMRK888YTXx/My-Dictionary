package com.xxmrk888ytxx.wordgroupscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.wordgroupscreen.contract.ProvideWordGroupContract
import com.xxmrk888ytxx.wordgroupscreen.contract.RemoveWordGroupContract
import com.xxmrk888ytxx.wordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordgroupscreen.models.ScreenState
import com.xxmrk888ytxx.wordgroupscreen.models.WordGroupDialogOptionState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordGroupViewModel @Inject constructor(
    private val provideWordGroupContract: ProvideWordGroupContract,
    private val removeWordGroupContract: RemoveWordGroupContract
) : ViewModel(),UiModel<ScreenState> {


    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
           is LocalUiEvent.FloatButtonClickEvent,is LocalUiEvent.AddFirstWordGroupButtonClickEvent -> {
               val navigator = (event as? LocalUiEvent.FloatButtonClickEvent)?.navigator
                   ?: (event as? LocalUiEvent.AddFirstWordGroupButtonClickEvent)?.navigator ?: return

               navigator.toCreateWordGroupScreen()
           }

            is LocalUiEvent.OpenWordGroupEvent -> {
                event.navigator.toViewGroupWordsScreen(event.wordGroup.id)
            }

            is LocalUiEvent.HideWordGroupDialogOption -> {
                wordGroupDialogOptionStateFlow.update { WordGroupDialogOptionState.Hidden }
            }

            is LocalUiEvent.RemoveWordGroupEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    removeWordGroupContract.removeWordGroup(event.wordGroupId)
                }
            }

            is LocalUiEvent.ShowWordGroupDialogOptionState -> {
                wordGroupDialogOptionStateFlow.update { WordGroupDialogOptionState.Showed(event.wordGroupId) }
            }
        }
    }

    private val wordGroupDialogOptionStateFlow:MutableStateFlow<WordGroupDialogOptionState> =
        MutableStateFlow(WordGroupDialogOptionState.Hidden)

    override val state: Flow<ScreenState> = combine(
        provideWordGroupContract.wordsGroup,
        wordGroupDialogOptionStateFlow
    ) { wordList,wordGroupDialogOptionState ->
        ScreenState(wordList.toImmutableList(),wordGroupDialogOptionState)
    }

    private var cachedScreenState:ScreenState = ScreenState()


    override val defValue: ScreenState
        get() = cachedScreenState


}