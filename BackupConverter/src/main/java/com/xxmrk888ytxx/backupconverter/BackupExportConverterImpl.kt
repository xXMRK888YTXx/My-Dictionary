package com.xxmrk888ytxx.backupconverter

import com.xxmrk888ytxx.backupconverter.models.BackupHeader
import com.xxmrk888ytxx.backupconverter.models.InternalBackupOutputLanguagesListModel
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.Const
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class BackupExportConverterImpl(
    private val logger: Logger
) : BackupExportConverter {
    override suspend fun languageListToJsonString(languagesBackupModel: List<LanguagesBackupModel>): String = withContext(Dispatchers.Default) {
        return@withContext try {
            Json.encodeToString(InternalBackupOutputLanguagesListModel.serializer(),InternalBackupOutputLanguagesListModel(languagesBackupModel))
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        }
    }

    override suspend fun wordGroupToJsonString(wordGroupBackupModel: WordGroupBackupModel): String = withContext(Dispatchers.Default) {
        return@withContext try {
            Json.encodeToString(WordGroupBackupModel.serializer(),wordGroupBackupModel)
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        }
    }

    override suspend fun getJsonHeader(): String = withContext(Dispatchers.Default) {
        return@withContext try {
            Json.encodeToString(BackupHeader.serializer(),BackupHeader(
                version = Const.TARGET_BACKUP_VERSION,
                createTime = System.currentTimeMillis()
            ))
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        }
    }

    companion object {
        const val LOG_TAG = "BackupExportConverterImpl"
    }
}