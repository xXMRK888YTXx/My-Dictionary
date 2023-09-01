package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CreateBackupContract
import com.xxmrk888ytxx.backupworker.BackupWorkerManager
import javax.inject.Inject

class CreateBackupContractImpl @Inject constructor(
    private val backupWorkerManager: BackupWorkerManager
) : CreateBackupContract {

    override suspend fun createBackup() {
        backupWorkerManager.startSingleBackupToTelegramWorker()
    }
}