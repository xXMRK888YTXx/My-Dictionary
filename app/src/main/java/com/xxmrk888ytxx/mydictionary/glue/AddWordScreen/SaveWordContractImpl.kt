package com.xxmrk888ytxx.mydictionary.glue.AddWordScreen

import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordContract
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import javax.inject.Inject

class SaveWordContractImpl @Inject constructor(
    private val wordRepository: WordRepository
) : SaveWordContract {

    override suspend fun saveWord(
        wordGroup: Int,
        wordText: String,
        translateWordText: String,
        transcriptText: String,
    ): Int {
        return wordRepository.addWord(WordModel(0,wordGroup,wordText,translateWordText,transcriptText))
    }
}