package com.xxmrk888ytxx.mydictionary.glue.BackupToTelegramWorker

import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.IsBackupNeededChecker
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupToTelegramLastBackupHashHolder.AutoBackupToTelegramLastBackupHashHolder
import javax.inject.Inject

class IsBackupNeededCheckerImpl @Inject constructor(
    private val autoBackupToTelegramLastBackupHashHolder: AutoBackupToTelegramLastBackupHashHolder
) : IsBackupNeededChecker {

    override suspend fun isNeeded(): Boolean {
        return autoBackupToTelegramLastBackupHashHolder.isHaveChanges()
    }
}