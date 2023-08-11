package com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder

import kotlinx.coroutines.flow.Flow

interface AutoBackupTelegramSettingsHolder {

    val state: Flow<AutoBackupTelegramSettings>

    suspend fun setBackupState(state:Boolean)

    suspend fun setBackupTime(backupTime: BackupTime)

    suspend fun setNotExecuteIfNotChangesState(state: Boolean)

}