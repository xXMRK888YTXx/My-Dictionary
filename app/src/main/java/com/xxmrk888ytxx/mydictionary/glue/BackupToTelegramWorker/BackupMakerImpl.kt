package com.xxmrk888ytxx.mydictionary.glue.BackupToTelegramWorker

import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.BackupException
import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.BackupMaker
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupToTelegramUseCase.CreateBackupToTelegramUseCase
import javax.inject.Inject

class BackupMakerImpl @Inject constructor(
    private val createBackupToTelegramUseCase: CreateBackupToTelegramUseCase
) : BackupMaker {

    override suspend fun doBackup() {
        try {
            createBackupToTelegramUseCase.execute()
        }catch (e:Exception) {
            throw BackupException()
        }
    }
}