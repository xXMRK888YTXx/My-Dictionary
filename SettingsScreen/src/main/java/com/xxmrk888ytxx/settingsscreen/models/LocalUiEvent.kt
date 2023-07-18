package com.xxmrk888ytxx.settingsscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent

sealed class LocalUiEvent : UiEvent {

    object OpenSourceCodeEvent : LocalUiEvent()

    object WriteToDeveloperEvent : LocalUiEvent()

    object OpenPrivacyPolicy : LocalUiEvent()

    object OpenTermsOfUse : LocalUiEvent()
}
