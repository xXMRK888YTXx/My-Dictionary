package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.translatorscreen.contract.FastSaveWordInWordGroupContract
import javax.inject.Inject

class FastSaveWordInWordGroupContractImpl @Inject constructor(
    private val wordRepository: WordRepository
) : FastSaveWordInWordGroupContract {

    override suspend fun saveWord(
        wordGroupId: Int,
        word: String,
        translation: String,
        transcription: String,
    ): Result<Unit> {
        return try {
            wordRepository.addWord(WordModel(
                wordGroupId = wordGroupId,
                wordText = word,
                translateText = translation,
                transcriptionText = transcription
            ))

            Result.success(Unit)
        } catch (e:Exception) {
            Result.failure(e)
        }
    }
}