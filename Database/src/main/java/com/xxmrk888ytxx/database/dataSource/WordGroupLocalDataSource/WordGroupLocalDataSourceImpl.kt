package com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource

import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.dao.WordGroupDao
import com.xxmrk888ytxx.database.entityes.LanguageEntity
import com.xxmrk888ytxx.database.entityes.WordGroupEntity
import com.xxmrk888ytxx.database.models.LanguageLocalModel
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class WordGroupLocalDataSourceImpl(
    private val languageDao: LanguageDao,
    private val wordGroupDao: WordGroupDao,
) : WordGroupLocalDataSource {

    override val wordGroupsFlow: Flow<List<WordGroupLocalModel>> = combine(
        languageDao.languageFlow.toMap(),
        wordGroupDao.wordGroupsFlow
    ) { languagesMap, wordGroupList ->
        wordGroupList.map {
            WordGroupLocalModel(
                it.id,
                name = it.name,
                languagesMap[it.primaryLanguageId]!!.toLocalDataModel(),
                languagesMap[it.secondaryLanguageId]!!.toLocalDataModel(),
                imageUrl = it.imageUrl
            )
        }
    }

    override fun getWordGroupById(wordGroupId: Int): Flow<WordGroupLocalModel> {
        return combine(
            languageDao.languageFlow.toMap(),
            wordGroupDao.getWordGroupById(wordGroupId)
        ) { languagesMap, wordGroup ->
            WordGroupLocalModel(
                wordGroup.id,
                name = wordGroup.name,
                languagesMap[wordGroup.primaryLanguageId]!!.toLocalDataModel(),
                languagesMap[wordGroup.secondaryLanguageId]!!.toLocalDataModel(),
                imageUrl = wordGroup.imageUrl
            )
        }
    }

    override suspend fun insertWordGroup(wordGroupLocalModel: WordGroupLocalModel) = withContext(Dispatchers.IO) {
        wordGroupDao.insertWordGroup(wordGroupLocalModel.toEntity())
    }

    override suspend fun removeWordGroup(id: Int) = withContext(Dispatchers.IO) {
        wordGroupDao.removeWordGroup(id)
    }

    private fun Flow<List<LanguageEntity>>.toMap(): Flow<Map<Int, LanguageEntity>> {
        return map {
            val map = mutableMapOf<Int, LanguageEntity>()

            it.forEach { entity ->
                map[entity.id] = entity
            }

            map
        }
    }

    private fun LanguageEntity.toLocalDataModel(): LanguageLocalModel {
        return LanguageLocalModel(id, name)
    }

    private fun WordGroupLocalModel.toEntity() : WordGroupEntity {
        return WordGroupEntity(id,name,primaryLanguage.id,secondaryLanguage.id,imageUrl)
    }


}