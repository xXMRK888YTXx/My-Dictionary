package com.xxmrk888ytxx.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.entityes.LanguageEntity
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language

@Dao
internal interface LanguageDao {

    @get:Query("SELECT * FROM LANGUAGES")
    val languageFlow: Flow<List<LanguageEntity>>

    @Insert
    suspend fun insertLanguage(language: LanguageEntity)

    @Query("DELETE FROM LANGUAGES WHERE id = :id")
    suspend fun removeLanguage(id:Int)

    @Query("SELECT MAX(id) FROM LANGUAGES")
    suspend fun getLastId(): Int
}