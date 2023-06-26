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

    @Insert
    suspend fun insertWordGroup(wordGroupEntity: WordGroupEntity)

    @Query("DELETE FROM WORDGROUPS WHERE id = :id")
    suspend fun removeWordGroup(id:Int)
}