package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository

import com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource.WordGroupLocalDataSource
import com.xxmrk888ytxx.database.models.LanguageLocalModel
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordGroupRepositoryImpl @Inject constructor(
    private val wordGroupLocalDataSource: WordGroupLocalDataSource,
) : WordGroupRepository {
    override val wordGroupsFlow: Flow<List<WordGroupModel>> =
        wordGroupLocalDataSource.wordGroupsFlow.map { list ->
            list.map { it.toModel() }
        }

    override fun getWordGroupById(wordGroupId: Int): Flow<WordGroupModel> =
        wordGroupLocalDataSource.getWordGroupById(wordGroupId).map { it.toModel() }

    override suspend fun insertWordGroup(wordGroupModel: WordGroupModel) : Int {
        return wordGroupLocalDataSource.insertWordGroup(wordGroupModel.toDataSourceModel())
    }

    override suspend fun removeWordGroup(id: Int) {
        wordGroupLocalDataSource.removeWordGroup(id)
    }

    override suspend fun updateImage(id: Int, imagePath: String?) {
        wordGroupLocalDataSource.updateImage(id, imagePath)
    }

    private fun WordGroupLocalModel.toModel(): WordGroupModel {
        return WordGroupModel(
            id,
            name,
            primaryLanguageId,
            secondaryLanguageId,
            imageUrl
        )
    }

    private fun WordGroupModel.toDataSourceModel() = WordGroupLocalModel(
        id, name, primaryLanguageId, secondaryLanguageId, imageUrl
    )
}