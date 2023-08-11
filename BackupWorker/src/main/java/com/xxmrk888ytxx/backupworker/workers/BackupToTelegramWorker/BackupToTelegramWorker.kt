package com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

internal class BackupToTelegramWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context,workerParameters) {

    private val isNeedCheckWhatBackupNeeded:Boolean by lazy {
        workerParameters.inputData.getBoolean(IS_NEED_CHECK_WHAT_BACKUP_NEEDED,true)
    }

    private val isBackupNeededChecker:IsBackupNeededChecker by lazy {
        context.getDepsByApplication()
    }

    private val backupMaker:BackupMaker by lazy { context.getDepsByApplication() }

    private val logger:Logger by lazy { context.getDepsByApplication() }


    override suspend fun doWork(): Result {
        try {
            if(isNeedCheckWhatBackupNeeded && !isBackupNeededChecker.isNeeded()) {
                logger.info("Data not changed from last backup time", LOG_TAG)
                return Result.success()
            }

            backupMaker.doBackup()

            return Result.success()
        }catch (_:BackupException) {
            logger.error("Backup exception in backup time", LOG_TAG)

            return Result.retry()
        }catch (e:Exception) {
            logger.error("Unknown exception in backup time ${e.stackTraceToString()}", LOG_TAG)

            return Result.retry()
        }

    }

    companion object {
        const val IS_NEED_CHECK_WHAT_BACKUP_NEEDED = "IS_NEED_CHECK_WHAT_BACKUP_NEEDED"

        private const val LOG_TAG = "LOG_TAG"
    }
}