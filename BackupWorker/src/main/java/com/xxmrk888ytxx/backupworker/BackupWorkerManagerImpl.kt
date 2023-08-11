package com.xxmrk888ytxx.backupworker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.BackupToTelegramWorker

class BackupWorkerManagerImpl(
    private val context: Context
) : BackupWorkerManager {

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override fun startSingleBackupToTelegramWorker() {

        val inputData = Data.Builder()
            .putBoolean(BackupToTelegramWorker.IS_NEED_CHECK_WHAT_BACKUP_NEEDED,false)
            .build()

        val constraints = Constraints(
            requiredNetworkType = NetworkType.CONNECTED
        )

        val workRequest = OneTimeWorkRequest.Builder(BackupToTelegramWorker::class.java)
            .addTag("BackupToTelegramWorker")
            .setInputData(inputData)
            .setConstraints(constraints)
            .setExpedited(
                OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST
            )
            .build()

        workManager.enqueue(workRequest)
    }
}