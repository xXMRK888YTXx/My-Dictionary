package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository

import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSource
import com.xxmrk888ytxx.database.models.LanguageLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val languageLocalDataSource: LanguageLocalDataSource
) : LanguageRepository {

    override val languageFlow: Flow<List<LanguageModel>> = languageLocalDataSource.languageFlow.map { list ->
        list.map { it.toModel() }
    }

    override suspend fun insertLanguage(language: LanguageModel) : Int {
        return languageLocalDataSource.insertLanguage(language.toLocalDataModel())
    }

    override suspend fun removeLanguage(id: Int) {
        languageLocalDataSource.removeLanguage(id)
    }

    private fun LanguageModel.toLocalDataModel() : LanguageLocalModel = LanguageLocalModel(id,name)

    private fun LanguageLocalModel.toModel() : LanguageModel = LanguageModel(id,name)
}