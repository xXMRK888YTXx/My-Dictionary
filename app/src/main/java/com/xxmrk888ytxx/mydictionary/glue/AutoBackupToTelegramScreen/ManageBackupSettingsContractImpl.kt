package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ManageBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupTime
import com.xxmrk888ytxx.backupworker.BackupWorkerManager
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.AutoBackupTelegramSettingsHolder
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.BackupTime as DomainBackupTime

/**
 * DomainBackupTime = com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.BackupTime
 */
class ManageBackupSettingsContractImpl @Inject constructor(
    private val backupTelegramSettingsHolder: AutoBackupTelegramSettingsHolder,
    private val backupWorkerManager: BackupWorkerManager
) : ManageBackupSettingsContract {

    private data class TimeInfoForBackup(
        val timeValue:Long,
        val timeUnit: TimeUnit
    )

    private fun BackupTime.toTimeInfoForBackup() : TimeInfoForBackup {
        return when(this) {
            BackupTime.NONE -> error("BackupTime NONE can't be convert to TimeInfoForBackup")
            BackupTime.HOURS_6 -> TimeInfoForBackup(6,TimeUnit.HOURS)
            BackupTime.HOURS_12 -> TimeInfoForBackup(12,TimeUnit.HOURS)
            BackupTime.DAY_1 -> TimeInfoForBackup(1,TimeUnit.DAYS)
            BackupTime.WEEK_1 -> TimeInfoForBackup(7,TimeUnit.DAYS)
        }
    }

    override suspend fun setBackupState(state: Boolean) {
        backupTelegramSettingsHolder.setBackupState(state)

        if(state) {
            setBackupTime(BackupTime.DAY_1)
        } else {
            backupWorkerManager.cancelPeriodicBackupToTelegramWorker()
            setBackupTime(BackupTime.NONE)
        }
    }

    override suspend fun setBackupTime(backupTime: BackupTime) {
        backupTelegramSettingsHolder.setBackupTime(backupTime.toDomainTime())
        backupWorkerManager.cancelPeriodicBackupToTelegramWorker()
        if(backupTime != BackupTime.NONE) {
            val timeForBackup = backupTime.toTimeInfoForBackup()

            backupWorkerManager.setupPeriodicBackupToTelegramWorker(
                timeForBackup.timeValue,
                timeForBackup.timeUnit,
                isNeedCheckWhatBackupNeeded = backupTelegramSettingsHolder.state.first().isNoExecuteBackupIfNotChanges
            )
        }
    }

    override suspend fun setNotExecuteIfNotChangesState(state: Boolean) {
        backupTelegramSettingsHolder.setNotExecuteIfNotChangesState(state)
        backupWorkerManager.cancelPeriodicBackupToTelegramWorker()
        setBackupTime(backupTelegramSettingsHolder.state.first().backupTime.toModuleTime())
    }

    override suspend fun reset() {
        setBackupState(false)
        setNotExecuteIfNotChangesState(false)
    }


    private fun BackupTime.toDomainTime() : DomainBackupTime {
        return DomainBackupTime.values().firstOrNull { this.id == it.id } ?: DomainBackupTime.NONE
    }

    private fun DomainBackupTime.toModuleTime() : BackupTime {
        return BackupTime.values().firstOrNull { this.id == it.id } ?: BackupTime.NONE
    }
}