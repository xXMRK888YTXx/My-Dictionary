package com.xxmrk888ytxx.viewgroupwordsscreen

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordGroupInfoContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ViewGroupWordsViewModel @AssistedInject constructor(
    @Assisted private val wordGroupId:Int,
    private val provideWordForWordGroupContract: ProvideWordForWordGroupContract,
    private val provideWordGroupInfoContract: ProvideWordGroupInfoContract
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.FloatButtonClickEvent -> toAddNewWordScreen(event.navigator)

            is LocalUiEvent.ClickButtonForAddNewWordOnEmptyStateEvent -> toAddNewWordScreen(event.navigator)

            is LocalUiEvent.OnBackScreenEvent -> event.navigator.backScreen()
        }
    }

    private fun toAddNewWordScreen(navigator: Navigator) {
        navigator.toAddWordScreen(wordGroupId)
    }

    override val state: Flow<ScreenState> = combine(
        provideWordForWordGroupContract.getWords(wordGroupId),
        provideWordGroupInfoContract.getWordGroupInfo(wordGroupId)
    ) { wordList,wordGroupInfo ->
        ScreenState(wordGroupInfo,wordList)
    }

    private var cachedScreenState:ScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cachedScreenState


    @AssistedFactory
    interface Factory {
        fun create(wordGroupId:Int) : ViewGroupWordsViewModel
    }
}