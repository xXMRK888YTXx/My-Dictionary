package com.xxmrk888ytxx.wordgroupscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {
    class FloatButtonClickEvent(val navigator: Navigator) : LocalUiEvent()
    class AddFirstWordGroupButtonClickEvent(val navigator: Navigator): LocalUiEvent()

}
