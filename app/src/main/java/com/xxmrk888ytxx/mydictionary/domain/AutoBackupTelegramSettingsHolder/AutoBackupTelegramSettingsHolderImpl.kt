package com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.xxmrk888ytxx.autobackuptotelegramscreen.AutoBackupToTelegramScreen
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AutoBackupTelegramSettingsHolderImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) : AutoBackupTelegramSettingsHolder {


    private val isEnabledAutoBackupToTelegramKey = booleanPreferencesKey("isEnabledAutoBackupToTelegramKey")

    private val isNoExecuteBackupIfNotChangesKey = booleanPreferencesKey("isNoExecuteBackupIfNotChangesKey")

    private val backupTimeIdKey = intPreferencesKey("backupTimeIdKey")

    private val backupTimeFlow = preferencesStorage.getProperty(
        backupTimeIdKey,
        BackupTime.NONE.id
    ).map {
        return@map BackupTime.values().firstOrNull { backupTime -> backupTime.id == it } ?: BackupTime.NONE
    }

    override val state: Flow<AutoBackupTelegramSettings> = combine(
        preferencesStorage.getProperty(isEnabledAutoBackupToTelegramKey,false),
        preferencesStorage.getProperty(isNoExecuteBackupIfNotChangesKey,false),
        backupTimeFlow
    ) { isEnabledAutoBackupToTelegram,isNoExecuteBackupIfNotChanges,backupTime ->
        AutoBackupTelegramSettings(isEnabledAutoBackupToTelegram,isNoExecuteBackupIfNotChanges,backupTime)
    }

    override suspend fun setBackupState(state: Boolean) {
        preferencesStorage.writeProperty(isEnabledAutoBackupToTelegramKey,state)
    }

    override suspend fun setBackupTime(backupTime: BackupTime) {
        val id = (BackupTime.values().firstOrNull { backupTime.id == it.id } ?: BackupTime.NONE).id

        preferencesStorage.writeProperty(backupTimeIdKey,id)
    }


    override suspend fun setNotExecuteIfNotChangesState(state: Boolean) {
        preferencesStorage.writeProperty(isNoExecuteBackupIfNotChangesKey,state)
    }
}