package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.BackupMaker
import com.xxmrk888ytxx.backupworker.workers.BackupToTelegramWorker.IsBackupNeededChecker
import com.xxmrk888ytxx.mydictionary.glue.BackupToTelegramWorker.BackupMakerImpl
import com.xxmrk888ytxx.mydictionary.glue.BackupToTelegramWorker.IsBackupNeededCheckerImpl
import dagger.Binds
import dagger.Module

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
}