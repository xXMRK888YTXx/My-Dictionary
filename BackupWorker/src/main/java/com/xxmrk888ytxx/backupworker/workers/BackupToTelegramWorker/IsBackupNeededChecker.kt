package com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker

interface IsBackupNeededChecker {

    suspend fun isNeeded() : Boolean
}