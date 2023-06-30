package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository

import com.xxmrk888ytxx.database.models.WordPhraseLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import kotlinx.coroutines.flow.Flow

interface WordPhrasesRepository {

    val wordPhrasesFlowModel : Flow<List<WordPhraseModel>>

    suspend fun insertWordPhrase(wordPhraseModel: WordPhraseModel) : Int

    suspend fun removeWordPhrase(id:Int)
}