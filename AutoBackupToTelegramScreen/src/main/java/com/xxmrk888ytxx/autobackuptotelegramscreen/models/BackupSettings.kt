package com.xxmrk888ytxx.autobackuptotelegramscreen.models

data class BackupSettings(
    val isEnabled:Boolean = false,
    val backupTime: BackupTime = BackupTime.NONE,
    val isNotExecuteIfNotChanges:Boolean = false
)
