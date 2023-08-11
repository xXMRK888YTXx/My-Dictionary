package com.xxmrk888ytxx.autobackuptotelegramscreen.contract

import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupSettings
import kotlinx.coroutines.flow.Flow

interface ProvideBackupSettingsContract {

    val backupSettings: Flow<BackupSettings>
}