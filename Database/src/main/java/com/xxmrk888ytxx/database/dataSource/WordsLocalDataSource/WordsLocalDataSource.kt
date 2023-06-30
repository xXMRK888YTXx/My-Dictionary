package com.xxmrk888ytxx.database.dataSource.WordsLocalDataSource

import com.xxmrk888ytxx.database.entityes.WordEntity
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.database.models.WordLocalModel
import kotlinx.coroutines.flow.Flow

interface WordsLocalDataSource {

    fun getWordsFlow() : Flow<List<WordLocalModel>>

    suspend fun addWord(wordGroupLocalModel: WordLocalModel)

    suspend fun removeWord(id:Int)
}