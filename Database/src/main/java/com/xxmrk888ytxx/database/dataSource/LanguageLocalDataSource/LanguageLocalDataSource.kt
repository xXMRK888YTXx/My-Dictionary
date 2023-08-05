package com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource

import com.xxmrk888ytxx.database.models.LanguageLocalModel
import kotlinx.coroutines.flow.Flow


/**
 * [Ru]
 * Интерфейс для управления хранимыми языками приложения для групп слов
 */

/**
 * [En]
 * Interface for manipulating stored languages of application for groups of words
 */
interface LanguageLocalDataSource {

    val languageFlow: Flow<List<LanguageLocalModel>>

    suspend fun insertLanguage(language: LanguageLocalModel) : Int

    suspend fun removeLanguage(id:Int)
}