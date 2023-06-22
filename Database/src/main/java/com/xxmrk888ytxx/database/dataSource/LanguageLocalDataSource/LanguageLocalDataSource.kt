package com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource

import com.xxmrk888ytxx.database.models.LanguageLocalModel
import kotlinx.coroutines.flow.Flow

interface LanguageLocalDataSource {

    val languageFlow: Flow<List<LanguageLocalModel>>

    suspend fun insertLanguage(language: LanguageLocalModel)

    suspend fun removeLanguage(id:Int)
}