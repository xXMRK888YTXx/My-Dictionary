package com.xxmrk888ytxx.settingsscreen.models

import android.content.Context
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

sealed class LocalUiEvent : UiEvent {
    class OpenCreateBackupScreenEvent(val navigator: Navigator) : LocalUiEvent()

    class OpenRestoreBackupScreenEvent(val navigator: Navigator) : LocalUiEvent()

    class OpenLanguageManageScreen(val navigator: Navigator) : LocalUiEvent()

    object OpenSourceCodeEvent : LocalUiEvent()

    object WriteToDeveloperEvent : LocalUiEvent()

    object OpenPrivacyPolicy : LocalUiEvent()

    object OpenTermsOfUse : LocalUiEvent()

    class RequestBuyRemoveAdsEvent(val context: Context) : LocalUiEvent()

    class OpenAutoBackupToTelegramEvent(val navigator: Navigator) : LocalUiEvent()

    object RestorePurchasesEvent : LocalUiEvent()
}
