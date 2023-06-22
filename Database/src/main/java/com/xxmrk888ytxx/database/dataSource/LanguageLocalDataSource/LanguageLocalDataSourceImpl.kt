package com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource

import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.entityes.LanguageEntity
import com.xxmrk888ytxx.database.models.LanguageLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class LanguageLocalDataSourceImpl(
    private val languageDao: LanguageDao
) : LanguageLocalDataSource {
    override val languageFlow: Flow<List<LanguageLocalModel>> = languageDao.languageFlow.map { list ->
        list.map { it.toLanguageModel() }
    }

    override suspend fun insertLanguage(language: LanguageLocalModel) = withContext(Dispatchers.IO) {
        languageDao.insertLanguage(language.toLanguageEntity())
    }

    override suspend fun removeLanguage(id: Int) = withContext(Dispatchers.IO) {
        languageDao.removeLanguage(id)
    }

    private fun LanguageEntity.toLanguageModel() : LanguageLocalModel {
        return LanguageLocalModel(id,name)
    }

    private fun LanguageLocalModel.toLanguageEntity() : LanguageEntity {
        return LanguageEntity(id,name)
    }
}