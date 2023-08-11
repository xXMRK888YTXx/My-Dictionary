package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ManageBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupTime
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.AutoBackupTelegramSettingsHolder
import javax.inject.Inject
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.BackupTime as DomainBackupTime

/**
 * DomainBackupTime = com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.BackupTime
 */
class ManageBackupSettingsContractImpl @Inject constructor(
    private val backupTelegramSettingsHolder: AutoBackupTelegramSettingsHolder
) : ManageBackupSettingsContract {
    override suspend fun setBackupState(state: Boolean) {
        backupTelegramSettingsHolder.setBackupState(state)

        if(state) {
            setBackupTime(BackupTime.DAY_1)
        } else {
            setBackupTime(BackupTime.NONE)
        }
    }

    override suspend fun setBackupTime(backupTime: BackupTime) {
        backupTelegramSettingsHolder.setBackupTime(backupTime.toDomainTime())
    }

    override suspend fun setNotExecuteIfNotChangesState(state: Boolean) {
        backupTelegramSettingsHolder.setNotExecuteIfNotChangesState(state)
    }

    override suspend fun reset() {
        setBackupState(false)
        setNotExecuteIfNotChangesState(false)
    }


    private fun BackupTime.toDomainTime() : DomainBackupTime {
        return DomainBackupTime.values().firstOrNull { this.id == it.id } ?: DomainBackupTime.NONE
    }
}