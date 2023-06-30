package com.xxmrk888ytxx.mydictionary.glue.AddWordScreen

import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordPhraseContract
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import javax.inject.Inject

class SaveWordPhraseContractImpl @Inject constructor(
    private val wordPhrasesRepository: WordPhrasesRepository
) : SaveWordPhraseContract {

    override suspend fun savePhrase(wordId: Int, phraseText: String, translatePhrase: String) {
        wordPhrasesRepository.insertWordPhrase(WordPhraseModel(0,wordId,phraseText,translatePhrase))
    }
}