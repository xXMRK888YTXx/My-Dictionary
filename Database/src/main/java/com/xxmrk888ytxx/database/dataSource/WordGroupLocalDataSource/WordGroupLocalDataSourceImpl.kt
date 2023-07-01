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
    private val wordGroupDao: WordGroupDao,
) : WordGroupLocalDataSource {

    override val wordGroupsFlow: Flow<List<WordGroupLocalModel>> =
        wordGroupDao.wordGroupsFlow.map { list ->
            list.map { it.toLocalModel() }
        }

    override fun getWordGroupById(wordGroupId: Int): Flow<WordGroupLocalModel> =
        wordGroupDao.getWordGroupById(wordGroupId).map { it.toLocalModel() }

    override suspend fun insertWordGroup(wordGroupLocalModel: WordGroupLocalModel) =
        withContext(Dispatchers.IO) {
            wordGroupDao.insertWordGroup(wordGroupLocalModel.toEntity())
        }

    override suspend fun removeWordGroup(id: Int) = withContext(Dispatchers.IO) {
        wordGroupDao.removeWordGroup(id)
    }

    private fun WordGroupLocalModel.toEntity(): WordGroupEntity {
        return WordGroupEntity(id, name, primaryLanguageId, secondaryLanguageId, imageUrl)
    }

    private fun WordGroupEntity.toLocalModel(): WordGroupLocalModel {
        return WordGroupLocalModel(id, name, primaryLanguageId, secondaryLanguageId, imageUrl)
    }


}