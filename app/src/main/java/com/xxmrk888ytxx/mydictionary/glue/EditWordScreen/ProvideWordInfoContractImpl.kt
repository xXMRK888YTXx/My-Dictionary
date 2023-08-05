package com.xxmrk888ytxx.mydictionary.glue.EditWordScreen

import com.xxmrk888ytxx.addwordscreen.contracts.ProvideWordInfoContract
import com.xxmrk888ytxx.addwordscreen.models.WordModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import javax.inject.Inject

class ProvideWordInfoContractImpl @Inject constructor(
    private val wordRepository: WordRepository
) : ProvideWordInfoContract {

    override suspend fun getWordInfo(wordId: Int): WordModel {
        return wordRepository.getWordById(wordId).toModuleModel()
    }

    private fun com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel.toModuleModel() : WordModel {
        return WordModel(wordText,translateText,transcriptionText)
    }
}