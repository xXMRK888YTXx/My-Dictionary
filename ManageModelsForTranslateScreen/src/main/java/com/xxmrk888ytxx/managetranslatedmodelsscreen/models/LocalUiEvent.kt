package com.xxmrk888ytxx.managetranslatedmodelsscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed interface LocalUiEvent : UiEvent {

    class BackScreenEvent(val navigator: Navigator) : LocalUiEvent

    class RemoveTranslateModel(val code: String) : LocalUiEvent
}