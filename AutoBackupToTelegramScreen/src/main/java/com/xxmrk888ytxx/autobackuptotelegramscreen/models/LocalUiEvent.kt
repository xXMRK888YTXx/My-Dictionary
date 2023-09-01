package com.xxmrk888ytxx.autobackuptotelegramscreen.models

import androidx.compose.material3.SnackbarHostState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

internal sealed class LocalUiEvent : UiEvent {
    
    class SaveTelegramDataEvent(val snackbarHostState: SnackbarHostState) : LocalUiEvent()

    class UserIdTextChangedEvent(val value: String) : LocalUiEvent()

    class BotKeyTextChangedEvent(val value: String) : LocalUiEvent()

    class OnBackEvent(val navigator: Navigator) : LocalUiEvent()
    
    class BackupStateChanged(val value: Boolean) : LocalUiEvent()
    
    class BackupTimeChangedEvent(val backupTime: BackupTime) : LocalUiEvent()
    
    class IsNotExecuteIfNotChangesStateChangedEvent(val value: Boolean) : LocalUiEvent()

    object WhereToGetEvent : LocalUiEvent()

    object CreateBackup : LocalUiEvent()

    object RemoveTelegramData : LocalUiEvent()

}
