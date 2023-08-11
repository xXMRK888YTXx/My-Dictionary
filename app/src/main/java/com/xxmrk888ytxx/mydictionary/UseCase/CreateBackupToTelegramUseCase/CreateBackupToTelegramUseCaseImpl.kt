package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupToTelegramUseCase

import android.content.Context
import androidx.core.net.toUri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupFileUseCase.CreateBackupFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase.ReadFileUseCase
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupToTelegramLastBackupHashHolder.AutoBackupToTelegramLastBackupHashHolder
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.TelegramDataHolder
import com.xxmrk888ytxx.telegramapi.TelegramApi
import com.xxmrk888ytxx.telegramapi.TelegramApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CreateBackupToTelegramUseCaseImpl @Inject constructor(
    private val createBackupFileUseCase: CreateBackupFileUseCase,
    private val context: Context,
    private val logger: Logger,
    private val wordGroupRepository: WordGroupRepository,
    private val readFileUseCase: ReadFileUseCase,
    private val autoBackupToTelegramLastBackupHashHolder: AutoBackupToTelegramLastBackupHashHolder,
    private val telegramApiFactory: TelegramApiFactory,
    private val telegramDataHolder: TelegramDataHolder
) : CreateBackupToTelegramUseCase {

    private suspend fun provideCreateBackupDir() : File {
        val createBackupDir = File(context.cacheDir,"CreateBackupToTelegramDir")

        if(createBackupDir.exists()) {
            createBackupDir.deleteRecursively()
        }

        createBackupDir.mkdir()

        return createBackupDir
    }

    override suspend fun execute() = withContext(Dispatchers.IO) {
        try {
            val backupFile = createBackupFileUseCase.execute(
                wordGroupRepository.wordGroupsFlow.first().toSet(),
                provideCreateBackupDir()
            )

            val telegramData = telegramDataHolder.telegramData.first() ?: throw IllegalStateException("Missing telegram data")

            val telegramApi = telegramApiFactory.create(
                userId = telegramData.userId,
                botKey = telegramData.botKey
            )

            telegramApi.uploadFile(
                readFileUseCase.execute(backupFile.toUri()).getOrThrow(),
            ).getOrThrow()

            autoBackupToTelegramLastBackupHashHolder.updateHash()

        }catch (e:Throwable) {
            logger.error(e, LOG_TAG)
            throw e
        } finally {
            withContext(NonCancellable) {
                provideCreateBackupDir().deleteRecursively()
            }
        }
    }

    companion object {
        private const val LOG_TAG = "CreateBackupToTelegramUseCaseImpl"
    }
}