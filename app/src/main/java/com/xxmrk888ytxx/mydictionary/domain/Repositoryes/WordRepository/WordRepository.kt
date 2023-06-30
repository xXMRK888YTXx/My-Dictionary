package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository

import com.xxmrk888ytxx.database.models.WordLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    fun getWordsFlow() : Flow<List<WordModel>>

    suspend fun addWord(wordModel: WordModel) : Int

    suspend fun removeWord(id:Int)
}