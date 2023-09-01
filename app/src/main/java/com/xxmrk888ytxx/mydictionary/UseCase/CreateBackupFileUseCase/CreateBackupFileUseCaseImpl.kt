package com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupFileUseCase

import android.content.Context
import com.xxmrk888ytxx.archivercreator.ArchiverCreator
import com.xxmrk888ytxx.backupconverter.BackupExportConverter
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.PhrasesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.Const
import com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase.FileWriterUseCase
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileFilter
import javax.inject.Inject

class CreateBackupFileUseCaseImpl @Inject constructor(
    private val languageRepository: LanguageRepository,
    private val wordRepository: WordRepository,
    private val backupExportConverter: BackupExportConverter,
    private val phrasesRepository: WordPhrasesRepository,
    private val archiverCreator: ArchiverCreator,
    private val fileWriterUseCase: FileWriterUseCase,
) : CreateBackupFileUseCase {


    override suspend fun execute(backupWordGroups: Set<WordGroupModel>,folderForBackup:File): File = withContext(Dispatchers.IO) {
        //Collect languages
        val languageMap = getLanguageMap(languageRepository.languageFlow.first())
        //

        //Collect words
        val backupWordGroupsModels = backupWordGroups.map {
            collectInfoForWordGroupBackupModel(it) { id -> languageMap[id]!!.backupId }
        }.map { it.await() }
        //

        //Creating backup dir

        val output = File(folderForBackup,"output")

        val backupFile = archiverCreator.createArchiver(output)

        val languageFile = File(folderForBackup, Const.BACKUP_LANGUAGE_FILE_NAME)
        //

        //Write languages
        val writeLanguageJob = launch { writeLanguages(languageFile,languageMap.map { it.value }) }
        //

        //Write groups of words
        backupWordGroupsModels.forEachIndexed { index, wordGroupBackupModel ->
            val wordGroupBackupFolder = File(folderForBackup,"${index}_${wordGroupBackupModel.name}")

            wordGroupBackupFolder.mkdir()

            val backupDataFile = File(wordGroupBackupFolder, Const.BACKUP_FILE_NAME)

            fileWriterUseCase.toFile(
                backupDataFile,
                backupExportConverter.wordGroupToJsonString(wordGroupBackupModel).toByteArray()
            )
        }
        //

        //Create header
        val headerFile = File(folderForBackup, Const.HEADER_FILE_NAME)

        fileWriterUseCase.toFile(headerFile,backupExportConverter.getJsonHeader().toByteArray())
        //

        //Adding files in archive
        writeLanguageJob.join()

        val fileFilter = FileFilter { pathname -> pathname?.name != backupFile.fileLocation.name }

        folderForBackup.listFiles(fileFilter)?.forEach {
            backupFile.addFile(it)
        }

        backupFile.close()

        return@withContext output
        //
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

    private suspend fun getLanguageMap(languageList:List<LanguageModel>) : Map<Int, LanguagesBackupModel> {
        val finalMap = mutableMapOf<Int, LanguagesBackupModel>()

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
}