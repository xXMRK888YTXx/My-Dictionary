package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.xxmrk888ytxx.archivercreator.ArchiverCreator
import com.xxmrk888ytxx.backupconverter.BackupExportConverter
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.PhrasesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.Const.BACKUP_FILE_NAME
import com.xxmrk888ytxx.coreandroid.Const.BACKUP_LANGUAGE_FILE_NAME
import com.xxmrk888ytxx.coreandroid.Const.HEADER_FILE_NAME
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupFileUseCase.CreateBackupFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase.FileWriterUseCase
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileFilter
import javax.inject.Inject

class CreateBackupUseCaseImpl @Inject constructor(
    private val context: Context,
    private val copyFileUseCase: CopyFileUseCase,
    private val logger:Logger,
    private val createBackupFileUseCase: CreateBackupFileUseCase
) : CreateBackupUseCase {

    private suspend fun provideCreateBackupDir() : File {
        val createBackupDir = File(context.cacheDir,"CreateBackupDir")

        if(createBackupDir.exists()) {
            createBackupDir.deleteRecursively()
        }

        createBackupDir.mkdir()

        return createBackupDir
    }

    override suspend fun execute(backupWordGroups: Set<WordGroupModel>, fileUri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val backupFile = createBackupFileUseCase.execute(backupWordGroups,provideCreateBackupDir())


            //Copy backup files in external storage
            copyFileUseCase.execute(
                backupFile.toUri(),
                fileUri
            )
            //

            Result.success(Unit)
        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            Result.failure(e)
        } finally {
            withContext(NonCancellable) {
                provideCreateBackupDir().deleteRecursively()
            }
        }

    }


    companion object {
        const val LOG_TAG = "CreateBackupUseCaseImpl"
    }
}

