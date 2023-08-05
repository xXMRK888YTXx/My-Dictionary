package com.xxmrk888ytxx.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.entityes.WordGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WordGroupDao {

    @get:Query("SELECT * FROM WORDGROUPS")
    val wordGroupsFlow: Flow<List<WordGroupEntity>>

    @Query("SELECT * FROM WORDGROUPS WHERE id = :wordGroupId")
    fun getWordGroupById(wordGroupId:Int) : Flow<WordGroupEntity>

    @Insert
    suspend fun insertWordGroup(wordGroupEntity: WordGroupEntity)

    @Query("DELETE FROM WORDGROUPS WHERE id = :id")
    suspend fun removeWordGroup(id:Int)

    @Query("SELECT MAX(id) FROM WORDGROUPS")
    suspend fun getLastId(): Int

    @Query("UPDATE WORDGROUPS SET imageUrl = :imagePath WHERE id = :id")
    suspend fun updateImage(id:Int,imagePath:String?)
}