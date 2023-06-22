package com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource

import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.entityes.WordGroupEntity
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import kotlinx.coroutines.flow.Flow

interface WordGroupLocalDataSource {

    val wordGroupsFlow: Flow<List<WordGroupLocalModel>>

    suspend fun insertWordGroup(wordGroupLocalModel: WordGroupLocalModel)

    suspend fun removeWordGroup(id:Int)
}