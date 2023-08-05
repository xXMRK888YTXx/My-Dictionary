package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository

import com.xxmrk888ytxx.database.models.LanguageLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {

    val languageFlow: Flow<List<LanguageModel>>

    suspend fun insertLanguage(language: LanguageModel) : Int

    suspend fun removeLanguage(id:Int)
}