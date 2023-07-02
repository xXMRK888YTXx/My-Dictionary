package com.xxmrk888ytxx.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.xxmrk888ytxx.database.entityes.WordPhraseEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WordPhraseDao {

    @get:Query("SELECT * FROM WordPhraseTable")
    val wordPhrasesFlow : Flow<List<WordPhraseEntity>>

    @Upsert
    suspend fun insertWordPhrase(wordPhraseEntity: WordPhraseEntity)

    @Query("SELECT * FROM WORDPHRASETABLE WHERE wordId = :wordId")
    suspend fun getPhrasesByWordId(wordId:Int) : List<WordPhraseEntity>

    @Query("DELETE FROM WordPhraseTable WHERE id = :id")
    suspend fun removeWordPhrase(id:Int)

    @Query("SELECT MAX(id) FROM WordPhraseTable")
    suspend fun getLastId() : Int
}