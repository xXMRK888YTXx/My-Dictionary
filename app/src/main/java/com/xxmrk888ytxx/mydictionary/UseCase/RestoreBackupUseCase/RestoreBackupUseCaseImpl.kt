package com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.xxmrk888ytxx.archivercreator.ArchiverCreator
import com.xxmrk888ytxx.backupconverter.BackupImportConverter
import com.xxmrk888ytxx.coreandroid.Const
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase.ReadFileUseCase
import com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider.RestoreBackupStrategyProvider
import com.xxmrk888ytxx.restorebackupscreen.exception.FileNotBackupFileException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipException
import javax.inject.Inject
import kotlin.text.Charsets.UTF_8

class RestoreBackupUseCaseImpl @Inject constructor(
    private val copyFileUseCase: CopyFileUseCase,
    private val context: Context,
    private val archiverCreator: ArchiverCreator,
    private val readFileUseCase: ReadFileUseCase,
    private val backupImportConverter: BackupImportConverter,
    private val restoreBackupStrategyProvider: RestoreBackupStrategyProvider,
    private val logger:Logger
) : RestoreBackupUseCase {

    private suspend fun provideCreateBackupDir() : File {
        val createBackupDir = File(context.cacheDir,"RestoreBackupDir")

        if(createBackupDir.exists()) {
            createBackupDir.deleteRecursively()
        }

        createBackupDir.mkdir()

        return createBackupDir
    }

    override suspend fun execute(backupFileUri: Uri): Result<Unit>  {
         return try {
            val backupDir = provideCreateBackupDir()

            val backupFile = File(backupDir,"backupFile")

            copyFileUseCase.execute(backupFileUri,backupFile.toUri())

            try {
                val archiveBackupFile =  archiverCreator.createArchiver(backupFile)

                archiveBackupFile.extractTo(backupDir)
            }catch (e:ZipException) {
                return Result.failure(FileNotBackupFileException())
            } finally {
                backupFile.delete()
            }

            val headerFile = File(backupDir,Const.HEADER_FILE_NAME)

            if(!headerFile.exists()) {
                return Result.failure(FileNotBackupFileException("Header file not found"))
            }

            val headerFileJsonString = readFileUseCase.execute(headerFile.toUri())
                .getOrThrow()
                .toString(UTF_8)

            val headerInfo = backupImportConverter.jsonHeaderStringToModel(headerFileJsonString)

            restoreBackupStrategyProvider
                .provideStrategy(headerInfo.version)
                .restore(backupDir)

             Result.success(Unit)
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)
            Result.failure(e)
        } finally {
            withContext(NonCancellable) {
                provideCreateBackupDir().delete()
            }
        }
    }

    companion object {
        const val LOG_TAG = "RestoreBackupUseCaseImpl"
    }
}