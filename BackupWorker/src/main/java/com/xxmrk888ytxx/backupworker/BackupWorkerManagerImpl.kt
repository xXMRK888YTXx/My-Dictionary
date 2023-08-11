package com.xxmrk888ytxx.backupworker

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.BackupToTelegramWorker
import java.util.concurrent.TimeUnit

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
            .addTag("BackupToTelegramWorker_Single")
            .setInputData(inputData)
            .setConstraints(constraints)
            .setExpedited(
                OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST
            )
            .build()

        workManager.enqueue(workRequest)
    }

    override fun setupPeriodicBackupToTelegramWorker(
        timeValue: Long,
        timeUnit: TimeUnit,
        isNeedCheckWhatBackupNeeded:Boolean
    ) {
        val constraints = Constraints(
            requiredNetworkType = NetworkType.CONNECTED
        )

        val data = Data.Builder()
            .putBoolean(BackupToTelegramWorker.IS_NEED_CHECK_WHAT_BACKUP_NEEDED,isNeedCheckWhatBackupNeeded)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            BackupToTelegramWorker::class.java,
            timeValue,
            timeUnit
        )
            .addTag("BackupToTelegramWorker_Periodic")
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "BackupToTelegramWorker_Periodic",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    override fun cancelPeriodicBackupToTelegramWorker() {
        workManager.cancelUniqueWork("BackupToTelegramWorker_Periodic")
    }
}