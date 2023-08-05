package com.xxmrk888ytxx.backupconverter

import com.xxmrk888ytxx.backupconverter.models.BackupHeader
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger

/**
 * [Ru]
 * Интерфейс для конвертации данных бекапа в формате JSON в модели данных приложения
 */

/**
 * [En]
 * Interface for convert data of backup in JSON format to data models of application
 */
interface BackupImportConverter {

    /**
     * [Ru]
     * Конвертация хедера в формате JSON в модель для приложения
     */

    /**
     * [En]
     * Convert header in JSON format to model for the application
     */

    suspend fun jsonHeaderStringToModel(jsonHeaderString: String) : BackupHeader

    /**
     * [Ru]
     * Конвертация списка языков в формате JSON в модель для приложения
     */

    /**
     * [En]
     * Convert list of languages in JSON format to model for the application
     */

    suspend fun jsonLanguageToLanguageList(jsonString:String) : List<LanguagesBackupModel>

    /**
     * [Ru]
     * Конвертация группы слов в формате JSON в модель для приложения
     */

    /**
     * [En]
     * Convert group of words in JSON format to model for the application
     */

    suspend fun jsonWordGroupToWordGroup(jsonString: String) : WordGroupBackupModel

    companion object {
        fun create(logger: Logger) : BackupImportConverter {
            return BackupImportConverterImpl(logger)
        }
    }
}