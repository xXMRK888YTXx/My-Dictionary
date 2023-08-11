package com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
import androidx.core.content.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.xxmrk888ytxx.backupworker.R
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coreandroid.buildNotification
import com.xxmrk888ytxx.coreandroid.buildNotificationChannel
import com.xxmrk888ytxx.coredeps.DepsProvider.getDepsByApplication

internal class BackupToTelegramWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : CoroutineWorker(context,workerParameters) {

    override suspend fun getForegroundInfo(): ForegroundInfo {

        applicationContext.buildNotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Channel for backup notification"
        )

        val notification = applicationContext.buildNotification(NOTIFICATION_CHANNEL_ID) {
            setContentTitle("Backup in process")
            setContentText("Don't turn off internet")
            setSmallIcon(R.drawable.baseline_backup_24)
        }


        return ForegroundInfo(
            NOTIFICATION_ID,
            notification
        )
    }

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

        private const val NOTIFICATION_ID = 124

        private const val NOTIFICATION_CHANNEL_ID = "BACKUP_CHANNEL"
    }
}