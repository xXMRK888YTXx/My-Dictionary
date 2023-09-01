package com.xxmrk888ytxx.backupworker

import android.content.Context
import java.util.concurrent.TimeUnit

interface BackupWorkerManager {

    fun startSingleBackupToTelegramWorker()

    fun setupPeriodicBackupToTelegramWorker(
        timeValue:Long,
        timeUnit: TimeUnit,
        isNeedCheckWhatBackupNeeded:Boolean
    )

    fun cancelPeriodicBackupToTelegramWorker()

    companion object {
        fun create(context: Context) : BackupWorkerManager {
            return BackupWorkerManagerImpl(context)
        }
    }
}