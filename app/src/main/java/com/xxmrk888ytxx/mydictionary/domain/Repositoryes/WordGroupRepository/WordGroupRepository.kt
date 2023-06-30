package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository

import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.mydictionary.models.WordGroupModel
import kotlinx.coroutines.flow.Flow

interface WordGroupRepository {

    val wordGroupsFlow: Flow<List<WordGroupModel>>

    suspend fun insertWordGroup(wordGroupModel: WordGroupModel)

    suspend fun removeWordGroup(id:Int)
}