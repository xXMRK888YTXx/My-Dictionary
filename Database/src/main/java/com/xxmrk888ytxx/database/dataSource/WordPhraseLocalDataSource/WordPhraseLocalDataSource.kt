package com.xxmrk888ytxx.database.dataSource.WordPhraseLocalDataSource

import com.xxmrk888ytxx.database.entityes.WordPhraseEntity
import com.xxmrk888ytxx.database.models.WordPhraseLocalModel
import kotlinx.coroutines.flow.Flow


/**
 * [Ru]
 * Интерфейс для управления хранимыми фразами для слов
 * */

/**
 * [En]
 * Interface for manipulating stored phrases of words
 */
interface WordPhraseLocalDataSource {

    val wordPhrasesFlow : Flow<List<WordPhraseLocalModel>>

    suspend fun insertWordPhrase(wordPhraseLocalModel: WordPhraseLocalModel)

    suspend fun getPhrasesByWordId(wordId:Int) : List<WordPhraseLocalModel>

    suspend fun removeWordPhrase(id:Int)
}