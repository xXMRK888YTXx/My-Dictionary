package com.xxmrk888ytxx.viewgroupwordsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ViewGroupWordsViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId:Int,
    private val provideWordForWordGroupContract: ProvideWordForWordGroupContract
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.FloatButtonClickEvent -> toAddNewWordScreen(event.navigator)

            is LocalUiEvent.ClickButtonForAddNewWordOnEmptyStateEvent -> toAddNewWordScreen(event.navigator)
        }
    }

    private fun toAddNewWordScreen(navigator: Navigator) {

    }

    override val state: Flow<ScreenState> = provideWordForWordGroupContract.words.map { wordList ->
        val result = if(wordList.isEmpty()) ScreenState.EmptyState
        else ScreenState.ViewWords(wordList)

        result.also { state -> cachedScreenState = state }
    }

    private var cachedScreenState:ScreenState = ScreenState.EmptyState

    override val defValue: ScreenState
        get() = cachedScreenState


    @AssistedFactory
    interface Factory {
        fun create(wordGroupId:Int) : ViewGroupWordsViewModel
    }
}