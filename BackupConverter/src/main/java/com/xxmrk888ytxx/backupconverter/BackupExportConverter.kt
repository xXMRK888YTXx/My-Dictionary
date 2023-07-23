package com.xxmrk888ytxx.backupconverter

import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger

interface BackupExportConverter {

    suspend fun languageListToJsonString(languagesBackupModel: List<LanguagesBackupModel>) : String

    suspend fun wordGroupToJsonString(wordGroupBackupModel: WordGroupBackupModel) : String

    companion object {
        fun create(logger: Logger) : BackupExportConverter {
            return BackupExportConverterImpl(logger)
        }
    }
}