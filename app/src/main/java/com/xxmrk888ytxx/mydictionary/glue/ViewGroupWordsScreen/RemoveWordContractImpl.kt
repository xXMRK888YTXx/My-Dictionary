package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.RemoveWordContract
import javax.inject.Inject

class RemoveWordContractImpl @Inject constructor(
    private val wordRepository: WordRepository
) : RemoveWordContract {

    override suspend fun removeWord(id: Int) {
        wordRepository.removeWord(id)
    }
}