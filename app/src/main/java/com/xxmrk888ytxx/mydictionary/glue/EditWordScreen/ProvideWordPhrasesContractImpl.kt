package com.xxmrk888ytxx.mydictionary.glue.EditWordScreen

import com.xxmrk888ytxx.addwordscreen.contracts.ProvideWordPhrasesContract
import com.xxmrk888ytxx.addwordscreen.models.PhrasesModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import javax.inject.Inject

class ProvideWordPhrasesContractImpl @Inject constructor(
    private val phrasesRepository: WordPhrasesRepository
) : ProvideWordPhrasesContract {

    override suspend fun getPhrases(wordId: Int): List<PhrasesModel> {
        return phrasesRepository.getPhrasesByWordId(wordId).map {
            it.toModuleModel()
        }
    }

    private fun WordPhraseModel.toModuleModel() : PhrasesModel {
        return PhrasesModel(this.id,0,this.phraseText,this.translateText)
    }
}