package com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder

data class AutoBackupTelegramSettings(
    val isEnabled:Boolean,
    val isNoExecuteBackupIfNotChanges:Boolean,
    val backupTime: BackupTime,

)
