package com.xxmrk888ytxx.viewgroupwordsscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

internal sealed class LocalUiEvent : UiEvent {

    class FloatButtonClickEvent(val navigator: Navigator) : LocalUiEvent()

    class ClickButtonForAddNewWordOnEmptyStateEvent(val navigator: Navigator): LocalUiEvent()

    class OnBackScreenEvent(val navigator: Navigator) : LocalUiEvent()

    data class TextToSpeechEvent(val text: String): LocalUiEvent()
}
