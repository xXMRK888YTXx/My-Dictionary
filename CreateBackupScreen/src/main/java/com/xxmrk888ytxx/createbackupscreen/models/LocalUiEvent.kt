package com.xxmrk888ytxx.createbackupscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent

sealed class LocalUiEvent : UiEvent {

    object SelectBackupPlaceEvent : LocalUiEvent()

    data class ChangeWordGroupSelectStateEvent(val wordGroupId:Int) : LocalUiEvent()
}
