package com.xxmrk888ytxx.autobackuptotelegramscreen.contract

import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupTime

interface ManageBackupSettingsContract {

    suspend fun setBackupState(state:Boolean)

    suspend fun setBackupTime(backupTime: BackupTime)

    suspend fun setNotExecuteIfNotChangesState(state: Boolean)

    suspend fun reset()
}