package com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker

import kotlin.jvm.Throws

interface BackupMaker {

    @Throws(BackupException::class)
    suspend fun doBackup()
}