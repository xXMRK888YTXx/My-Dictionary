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
    private val languageRepository: LanguageRepository,
    private val wordRepository: WordRepository,
    private val backupExportConverter: BackupExportConverter,
    private val phrasesRepository: WordPhrasesRepository,
    private val context: Context,
    private val archiverCreator: ArchiverCreator,
    private val fileWriterUseCase: FileWriterUseCase,
    private val copyFileUseCase: CopyFileUseCase,
    private val logger:Logger
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
            //Collect languages
            val languageMap = getLanguageMap(languageRepository.languageFlow.first())
            //

            //Collect words
            val backupWordGroupsModels = backupWordGroups.map {
                collectInfoForWordGroupBackupModel(it) { id -> languageMap[id]!!.backupId }
            }.map { it.await() }
            //

            //Creating backup dir
            val backupDir = provideCreateBackupDir()

            val backupFile = archiverCreator.createArchiver(File(backupDir,"output"))

            val languageFile = File(backupDir,BACKUP_LANGUAGE_FILE_NAME)
            //

            //Write languages
            val writeLanguageJob = launch { writeLanguages(languageFile,languageMap.map { it.value }) }
            //

            //Write groups of words
            backupWordGroupsModels.forEachIndexed { index, wordGroupBackupModel ->
                val wordGroupBackupFolder = File(backupDir,"${index}_${wordGroupBackupModel.name}")

                wordGroupBackupFolder.mkdir()

                val backupDataFile = File(wordGroupBackupFolder,BACKUP_FILE_NAME)

                fileWriterUseCase.toFile(
                    backupDataFile,
                    backupExportConverter.wordGroupToJsonString(wordGroupBackupModel).toByteArray()
                )
            }
            //

            //Create header
            val headerFile = File(backupDir, HEADER_FILE_NAME)

            fileWriterUseCase.toFile(headerFile,backupExportConverter.getJsonHeader().toByteArray())
            //

            //Adding files in archive
            writeLanguageJob.join()

            val fileFilter = FileFilter { pathname -> pathname?.name != backupFile.fileLocation.name }

            backupDir.listFiles(fileFilter)?.forEach {
                backupFile.addFile(it)
            }

            backupFile.close()
            //


            //Copy backup files in external storage
            copyFileUseCase.execute(
                backupFile.fileLocation.toUri(),
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

    private suspend fun writeLanguages(
        file:File,
        languageList: List<LanguagesBackupModel>
    ) {
        fileWriterUseCase.toFile(
            file,
            backupExportConverter.languageListToJsonString(languageList).toByteArray()
        )
    }

    private suspend fun getLanguageMap(languageList:List<LanguageModel>) : Map<Int,LanguagesBackupModel> {
        val finalMap = mutableMapOf<Int,LanguagesBackupModel>()

        languageList.forEachIndexed { index, languageModel ->
            finalMap[languageModel.id] = languageModel.toBackupModel(index)
        }

        return finalMap
    }

    private fun LanguageModel.toBackupModel(backupId:Int) : LanguagesBackupModel {
        return LanguagesBackupModel(backupId,name)
    }

    private fun WordModel.toBackupModel(
        phrasesList:List<PhrasesBackupModel>
    ) : WordBackupModel {
        return WordBackupModel(
            wordText,this.translateText,this.transcriptionText,phrasesList
        )
    }

    private fun WordPhraseModel.toBackupModel() : PhrasesBackupModel {
        return PhrasesBackupModel(this.phraseText,this.translateText)
    }

    private suspend fun collectInfoForWordGroupBackupModel(
        wordGroupModel: WordGroupModel,
        onGetBackupLanguageId:(Int) -> Int
    ) = withContext(Dispatchers.IO) {
        async {
            val outputBackupWords = mutableListOf<WordBackupModel>()

            val words = wordRepository.getWordsByWordGroupId(wordGroupModel.id).first()

            words.forEach { word ->
                val phrases = phrasesRepository.getPhrasesByWordId(word.wordGroupId).map { it.toBackupModel() }

                outputBackupWords.add(
                    word.toBackupModel(phrases)
                )
            }

            return@async WordGroupBackupModel(
                wordGroupModel.name,
                primaryLanguageBackupId = withContext(Dispatchers.Main) { onGetBackupLanguageId(wordGroupModel.primaryLanguageId) } ,
                secondaryLanguageBackupId = withContext(Dispatchers.Main) { onGetBackupLanguageId(wordGroupModel.secondaryLanguageId) },
                words = outputBackupWords
            )
        }
    }

    companion object {
        const val LOG_TAG = "CreateBackupUseCaseImpl"
    }
}

