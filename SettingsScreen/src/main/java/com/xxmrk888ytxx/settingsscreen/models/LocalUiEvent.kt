package com.xxmrk888ytxx.settingsscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {
    class OpenCreateBackupScreenEvent(val navigator: Navigator) : LocalUiEvent()

    class OpenRestoreBackupScreenEvent(val navigator: Navigator) : LocalUiEvent()

    object OpenSourceCodeEvent : LocalUiEvent()

    object WriteToDeveloperEvent : LocalUiEvent()

    object OpenPrivacyPolicy : LocalUiEvent()

    object OpenTermsOfUse : LocalUiEvent()
}
