package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.backupworker.BackupWorkerManager
import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.BackupMaker
import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.IsBackupNeededChecker
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.mydictionary.glue.BackupToTelegramWorker.BackupMakerImpl
import com.xxmrk888ytxx.mydictionary.glue.BackupToTelegramWorker.IsBackupNeededCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface BackupToTelegramWorkerModule {

    @Binds
    fun bindBackupMaker(
        backupMakerImpl: BackupMakerImpl
    ) : BackupMaker

    @Binds
    fun bindsIsBackupNeededChecker(
        isBackupNeededCheckerImpl: IsBackupNeededCheckerImpl
    ) : IsBackupNeededChecker

    companion object {
        @Provides
        @AppScope
        fun bindsBackupWorkerManager(context: Context) : BackupWorkerManager {
            return BackupWorkerManager.create(context)
        }
    }
}