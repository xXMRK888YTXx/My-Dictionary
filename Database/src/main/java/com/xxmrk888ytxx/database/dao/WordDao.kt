package com.xxmrk888ytxx.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.xxmrk888ytxx.database.entityes.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WordDao {

    @Query("SELECT * FROM WordsTable")
    fun getWordsFlow() : Flow<List<WordEntity>>

    @Query("SELECT * FROM WORDSTABLE WHERE wordGroupId = :wordGroupId")
    fun getWordsByWordGroupIdFlow(wordGroupId:Int) : Flow<List<WordEntity>>

    @Upsert
    suspend fun insertWord(wordEntity: WordEntity)

    @Query("DELETE FROM WordsTable WHERE id = :id")
    suspend fun removeWord(id:Int)

    @Query("SELECT MAX(id) FROM WordsTable")
    suspend fun getLastId() : Int
}