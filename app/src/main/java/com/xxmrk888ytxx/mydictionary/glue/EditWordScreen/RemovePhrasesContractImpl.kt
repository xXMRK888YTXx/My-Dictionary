package com.xxmrk888ytxx.mydictionary.glue.EditWordScreen

import com.xxmrk888ytxx.addwordscreen.contracts.RemovePhrasesContract
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import javax.inject.Inject

class RemovePhrasesContractImpl @Inject constructor(
    private val phrasesRepository: WordPhrasesRepository
) : RemovePhrasesContract {

    override suspend fun removePhrases(id: Int) {
        phrasesRepository.removeWordPhrase(id)
    }
}