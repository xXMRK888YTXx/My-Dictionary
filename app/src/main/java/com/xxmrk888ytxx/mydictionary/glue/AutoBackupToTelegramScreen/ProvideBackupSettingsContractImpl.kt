package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ProvideBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupSettings
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupTime
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.AutoBackupTelegramSettingsHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.BackupTime as DomainBackupTime


/**
 * DomainBackupTime = com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.BackupTime
 */
class ProvideBackupSettingsContractImpl @Inject constructor(
    private val backupTelegramSettingsHolder: AutoBackupTelegramSettingsHolder
) : ProvideBackupSettingsContract {

    override val backupSettings: Flow<BackupSettings> = backupTelegramSettingsHolder.state.map {
        BackupSettings(
            isEnabled = it.isEnabled,
            backupTime = it.backupTime.toModuleTime(),
            isNotExecuteIfNotChanges = it.isNoExecuteBackupIfNotChanges
        )
    }

    private fun DomainBackupTime.toModuleTime() : BackupTime {
        return BackupTime.values().firstOrNull { this.id == it.id } ?: BackupTime.NONE
    }
}