package com.xxmrk888ytxx.viewgroupwordsscreen.models

import androidx.compose.foundation.lazy.LazyListState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import kotlinx.coroutines.CoroutineScope

internal sealed class LocalUiEvent : UiEvent {
    object HideWordOptionDialogEvent : LocalUiEvent()

    class ChangeSearchStateEvent() : LocalUiEvent()

    data class ShowWordOptionDialogEvent(val wordId: Int) : LocalUiEvent()

    class FloatButtonClickEvent(val navigator: Navigator) : LocalUiEvent()

    class ClickButtonForAddNewWordOnEmptyStateEvent(val navigator: Navigator): LocalUiEvent()

    class OnBackScreenEvent(val navigator: Navigator) : LocalUiEvent()

    data class TextToSpeechEvent(val text: String): LocalUiEvent()

    class OpenWordForEditEvent(val navigator: Navigator,val wordId: Int): LocalUiEvent()

    data class RemoveWordEvent(val wordId: Int) : LocalUiEvent()

    data class OnChangeSearchValueEvent(val value:String) : LocalUiEvent()
}
