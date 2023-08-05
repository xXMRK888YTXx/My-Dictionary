package com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.models.Word
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideWordForWordGroupContractImpl @Inject constructor(
    private val wordRepository: WordRepository
) : ProvideWordForWordGroupContract {

    override fun getWords(wordGroupId: Int): Flow<ImmutableList<Word>> {
        return wordRepository.getWordsByWordGroupId(wordGroupId).map { list ->
            list.map { it.toModuleWord() }.toImmutableList()
        }
    }

    private fun WordModel.toModuleWord() : Word {
        return Word(id, wordText, translateText, transcriptionText)
    }

}