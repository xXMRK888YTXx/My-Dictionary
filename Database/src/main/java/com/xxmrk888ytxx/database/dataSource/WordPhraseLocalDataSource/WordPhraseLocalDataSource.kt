package com.xxmrk888ytxx.database.dataSource.WordPhraseLocalDataSource

import com.xxmrk888ytxx.database.models.WordPhraseLocalModel
import kotlinx.coroutines.flow.Flow

interface WordPhraseLocalDataSource {

    val wordPhrasesFlow : Flow<List<WordPhraseLocalModel>>

    suspend fun insertWordPhrase(wordPhraseLocalModel: WordPhraseLocalModel)

    suspend fun removeWordPhrase(id:Int)
}