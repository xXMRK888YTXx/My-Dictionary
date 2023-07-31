package com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource

import androidx.room.Insert
import androidx.room.Query
import com.xxmrk888ytxx.database.entityes.WordGroupEntity
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Интерфейс для управления хранимыми группами слов приложения
 * */

/**
 * [En]
 * Interface for manipulating stored application's groups of word
 */
interface WordGroupLocalDataSource {

    val wordGroupsFlow: Flow<List<WordGroupLocalModel>>

    fun getWordGroupById(wordGroupId:Int) : Flow<WordGroupLocalModel>

    suspend fun insertWordGroup(wordGroupLocalModel: WordGroupLocalModel) : Int

    suspend fun removeWordGroup(id:Int)

    suspend fun updateImage(id:Int,imagePath:String?)
}