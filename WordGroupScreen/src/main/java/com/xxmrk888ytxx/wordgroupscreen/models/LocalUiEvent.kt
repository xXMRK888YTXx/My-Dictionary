package com.xxmrk888ytxx.wordgroupscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {
    class FloatButtonClickEvent(val navigator: Navigator) : LocalUiEvent()

    class AddFirstWordGroupButtonClickEvent(val navigator: Navigator): LocalUiEvent()

    class OpenWordGroupEvent(val navigator: Navigator,val wordGroup: WordGroup) : LocalUiEvent()

    object HideWordGroupDialogOption : LocalUiEvent()

    data class ShowWordGroupDialogOptionState(val wordGroupId:Int) : LocalUiEvent()

    data class RemoveWordGroupEvent(val wordGroupId: Int) : LocalUiEvent()

}
