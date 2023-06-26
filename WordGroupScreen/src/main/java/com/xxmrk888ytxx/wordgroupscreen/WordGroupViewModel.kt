package com.xxmrk888ytxx.wordgroupscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.wordgroupscreen.contract.ProvideWordGroupContract
import com.xxmrk888ytxx.wordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordgroupscreen.models.ScreenState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordGroupViewModel @Inject constructor(
    private val provideWordGroupContract: ProvideWordGroupContract
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
        }
    }

    override val state: Flow<ScreenState> = provideWordGroupContract.wordsGroup.map {
        val result = if(it.isEmpty()) ScreenState.EmptyWordGroupState
        else ScreenState.WordList(it.toImmutableList())

        result.also { state -> cachedScreenState = state }
    }

    private var cachedScreenState:ScreenState = ScreenState.EmptyWordGroupState


    override val defValue: ScreenState
        get() = cachedScreenState


}