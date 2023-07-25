package com.xxmrk888ytxx.backupconverter

import com.xxmrk888ytxx.backupconverter.models.BackupHeader
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger

interface BackupImportConverter {

    suspend fun jsonHeaderStringToModel(jsonHeaderString: String) : BackupHeader

    suspend fun jsonLanguageToLanguageList(jsonString:String) : List<LanguagesBackupModel>

    suspend fun jsonWordGroupToWordGroup(jsonString: String) : WordGroupBackupModel

    companion object {
        fun create(logger: Logger) : BackupImportConverter {
            return BackupImportConverterImpl(logger)
        }
    }
}