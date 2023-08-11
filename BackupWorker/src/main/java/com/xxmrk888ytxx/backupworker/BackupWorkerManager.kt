package com.xxmrk888ytxx.backupworker

import android.content.Context

interface BackupWorkerManager {

    fun startSingleBackupToTelegramWorker()

    companion object {
        fun create(context: Context) : BackupWorkerManager {
            return BackupWorkerManagerImpl(context)
        }
    }
}