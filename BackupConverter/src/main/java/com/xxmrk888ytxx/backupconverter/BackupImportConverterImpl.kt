package com.xxmrk888ytxx.backupconverter

import com.xxmrk888ytxx.backupconverter.models.BackupHeader
import com.xxmrk888ytxx.backupconverter.models.InternalBackupOutputLanguagesListModel
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class BackupImportConverterImpl(
    private val logger: Logger
) : BackupImportConverter {

    override suspend fun jsonHeaderStringToModel(jsonHeaderString: String): BackupHeader = withContext(Dispatchers.Default) {
        return@withContext try {
            Json.decodeFromString(jsonHeaderString)
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        }
    }

    override suspend fun jsonLanguageToLanguageList(jsonString: String): List<LanguagesBackupModel> = withContext(Dispatchers.Default) {
        return@withContext try {
            Json.decodeFromString<InternalBackupOutputLanguagesListModel>(jsonString).languagesList
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        }
    }

    override suspend fun jsonWordGroupToWordGroup(jsonString: String) : WordGroupBackupModel = withContext(Dispatchers.Default) {
        return@withContext try {
            Json.decodeFromString(jsonString)
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        }
    }

    companion object {
        const val LOG_TAG = "BackupImportConverterImpl"
    }
}