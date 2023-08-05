package com.xxmrk888ytxx.mydictionary.glue.EditWordScreen

import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordContract
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import javax.inject.Inject

class SaveWordContractImpl @Inject constructor(
    private val wordRepository: WordRepository
) : SaveWordContract {

    override suspend fun saveWord(
        wordId:Int,
        wordGroup: Int,
        wordText: String,
        translateWordText: String,
        transcriptText: String,
    ): Int {
        return wordRepository.addWord(WordModel(wordId,wordGroup,wordText,translateWordText,transcriptText))
    }
}