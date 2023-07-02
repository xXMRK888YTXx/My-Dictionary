package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import kotlinx.coroutines.flow.Flow

interface WordRepository {

    fun getWords() : Flow<List<WordModel>>

    fun getWordsByWordGroupId(wordGroupId:Int) : Flow<List<WordModel>>

    suspend fun getWordById(id:Int) : WordModel

    suspend fun addWord(wordModel: WordModel) : Int

    suspend fun removeWord(id:Int)
}