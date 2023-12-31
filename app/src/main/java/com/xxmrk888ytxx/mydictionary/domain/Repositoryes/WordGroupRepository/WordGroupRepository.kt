package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository

import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import kotlinx.coroutines.flow.Flow

interface WordGroupRepository {

    val wordGroupsFlow: Flow<List<WordGroupModel>>

    fun getWordGroupById(wordGroupId:Int) : Flow<WordGroupModel>

    suspend fun insertWordGroup(wordGroupModel: WordGroupModel) : Int

    suspend fun removeWordGroup(id:Int)

    suspend fun updateImage(id:Int,imagePath:String?)
}