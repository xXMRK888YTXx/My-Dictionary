package com.xxmrk888ytxx.database.dataSource.WordsLocalDataSource

import com.xxmrk888ytxx.database.entityes.WordEntity
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.database.models.WordLocalModel
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Интерфейс для управления хранимыми словами
 * */

/**
 * [En]
 * Interface for manipulating stored words
 */
interface WordsLocalDataSource {

    fun getWordsFlow() : Flow<List<WordLocalModel>>

    fun getWordsByWordGroupIdFlow(wordGroupId:Int) : Flow<List<WordLocalModel>>

    suspend fun getWordById(id:Int) : WordLocalModel

    suspend fun addWord(wordGroupLocalModel: WordLocalModel) : Int

    suspend fun removeWord(id:Int)
}