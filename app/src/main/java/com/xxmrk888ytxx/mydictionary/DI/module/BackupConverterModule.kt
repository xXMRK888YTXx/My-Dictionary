package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.backupconverter.BackupExportConverter
import com.xxmrk888ytxx.backupconverter.BackupImportConverter
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import dagger.Module
import dagger.Provides

@Module
interface BackupConverterModule {
    companion object {
        @Provides
        fun provideBackupExportConverter(logger: Logger) : BackupExportConverter {
            return BackupExportConverter.create(logger)
        }

        @Provides
        fun provideBackupImportConverter(logger: Logger) : BackupImportConverter {
            return BackupImportConverter.create(logger)
        }
    }
}