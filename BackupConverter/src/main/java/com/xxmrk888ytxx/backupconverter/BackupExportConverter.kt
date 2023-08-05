package com.xxmrk888ytxx.backupconverter

import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger

/**
 * [Ru]
 *  Интерфейс для конвертации данных для бэкапа в json
 */

/**
 * [En]
 * Interface for converting data for backup in JSON format
 */
interface BackupExportConverter {

    /**
     * [Ru]
     * Преобразует модель резервного копирования с языками в JSON формат
     */

    /**
     * [En]
     * Converting model of backup with languages in JSON format
     */
    suspend fun languageListToJsonString(languagesBackupModel: List<LanguagesBackupModel>) : String

    /**
     * [Ru]
     * Преобразует модель резервного копирования с языками в JSON формат
     */

    /**
     * [En]
     * Convering model of backup with groups of words in JSON format
     */
    suspend fun wordGroupToJsonString(wordGroupBackupModel: WordGroupBackupModel) : String

    /**
     * [Ru]
     * Генерирует JSON модель с актуальным для данной версии приложением хедором
     */

    /**
     * [En]
     * Generating a JSON model with the actual header for the current version of the application
     */
    suspend fun getJsonHeader() : String

    companion object {
        fun create(logger: Logger) : BackupExportConverter {
            return BackupExportConverterImpl(logger)
        }
    }
}