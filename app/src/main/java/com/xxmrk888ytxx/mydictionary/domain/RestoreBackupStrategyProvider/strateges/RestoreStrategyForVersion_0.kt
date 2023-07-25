package com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider.strateges

import androidx.core.net.toUri
import com.xxmrk888ytxx.addwordscreen.models.PhrasesModel
import com.xxmrk888ytxx.backupconverter.BackupImportConverter
import com.xxmrk888ytxx.backupconverter.models.LanguagesBackupModel
import com.xxmrk888ytxx.backupconverter.models.PhrasesBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordBackupModel
import com.xxmrk888ytxx.backupconverter.models.WordGroupBackupModel
import com.xxmrk888ytxx.coreandroid.Const
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase.ReadFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase.exception.BadBackupFileException
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider.RestoreBackupStrategy
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileFilter
import javax.inject.Inject
import kotlin.text.Charsets.UTF_8

class RestoreStrategyForVersion_0 @Inject constructor(
    private val fileReadFileUseCase: ReadFileUseCase,
    private val languageRepository: LanguageRepository,
    private val backupImportConverter: BackupImportConverter,
    private val wordGroupRepository: WordGroupRepository,
    private val wordRepository: WordRepository,
    private val phrasesRepository: WordPhrasesRepository,
    private val logger: Logger
) : RestoreBackupStrategy {

    override suspend fun restore(backupDir: File) = withContext(Dispatchers.IO) {
        try {
            val restoreLanguageDeferred:Deferred<Map<Int,LanguageModel>> = async(Dispatchers.IO) {
                val languageJsonString = fileReadFileUseCase.execute(
                    File(backupDir,Const.BACKUP_LANGUAGE_FILE_NAME).toUri()
                ).getOrThrow().toString(UTF_8)

                val languageModelList = backupImportConverter.jsonLanguageToLanguageList(languageJsonString)

                val outputMap = mutableMapOf<Int,LanguageModel>()

                languageModelList.forEach {
                    val model = it.toModel()

                    outputMap[it.backupId] = model.copy(id = languageRepository.insertLanguage(model))
                }

                outputMap
            }

            val fileFilter = FileFilter {
                it.isDirectory
            }

            val wordGroupDirList = backupDir.listFiles(fileFilter) ?: throw BadBackupFileException("Word groups dirs is not found")

            val languageMap = restoreLanguageDeferred.await()

            wordGroupDirList.forEach { wordGroupDir ->
                val backupDataFile = File(wordGroupDir,Const.BACKUP_FILE_NAME)

                val backupJsonString = fileReadFileUseCase.execute(backupDataFile.toUri())
                    .getOrThrow()
                    .toString(UTF_8)

                val wordGroupBackupModel = backupImportConverter
                    .jsonWordGroupToWordGroup(backupJsonString)

                val wordGroupId = wordGroupRepository.insertWordGroup(
                    wordGroupBackupModel.toModel(
                        primaryLanguageId = languageMap[wordGroupBackupModel.primaryLanguageBackupId]?.id ?: throw BadBackupFileException("Not found language for restoring word group"),
                        secondaryLanguageId = languageMap[wordGroupBackupModel.secondaryLanguageBackupId]?.id ?: throw BadBackupFileException("Not found language for restoring word group")
                    )
                )

                wordGroupBackupModel.words.forEach { wordBackupModel ->
                    val wordId = wordRepository.addWord(wordBackupModel.toModel(wordGroupId))

                    wordBackupModel.phrases.forEach { phraseBackupModel ->
                        phrasesRepository.insertWordPhrase(phraseBackupModel.toModel(wordId))
                    }
                }


            }
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)

            val exception = if(e is BadBackupFileException) e else BadBackupFileException(e.message,e)

            throw exception
        }
    }

    private fun PhrasesBackupModel.toModel(wordId:Int) : WordPhraseModel {
        return WordPhraseModel(
            id = 0,
            wordId = wordId,
            phraseText = phrasesText,
            translateText = this.phrasesTranslate
        )
    }

    private fun WordBackupModel.toModel(wordGroupId:Int) : WordModel {
        return WordModel(
            id = 0,
            wordGroupId = wordGroupId,
            wordText = wordText,
            translateText = this.wordTranslate,
            transcriptionText = this.wordTranscription
        )
    }

    private fun LanguagesBackupModel.toModel() : LanguageModel {
        return LanguageModel(0,this.name)
    }

    private fun WordGroupBackupModel.toModel(
        primaryLanguageId:Int,
        secondaryLanguageId:Int
    ) : WordGroupModel {
        return WordGroupModel(
            id = 0,
            name = this.name,
            primaryLanguageId = primaryLanguageId,
            secondaryLanguageId = secondaryLanguageId,
            imageUrl = null
        )
    }

    companion object {
        private const val LOG_TAG = "RestoreStrategyForVersion_0"
    }
}