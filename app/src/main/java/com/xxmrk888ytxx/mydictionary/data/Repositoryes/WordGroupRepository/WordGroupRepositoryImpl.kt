package com.xxmrk888ytxx.mydictionary.data.Repositoryes.WordGroupRepository

import com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource.WordGroupLocalDataSource
import com.xxmrk888ytxx.database.models.LanguageLocalModel
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.mydictionary.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.models.WordGroupModel
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

    override suspend fun insertWordGroup(wordGroupModel: WordGroupModel) {
        wordGroupLocalDataSource.insertWordGroup(wordGroupModel.toDataSourceModel())
    }

    override suspend fun removeWordGroup(id: Int) {
        wordGroupLocalDataSource.removeWordGroup(id)
    }

    private fun WordGroupLocalModel.toModel(): WordGroupModel {
        return WordGroupModel(id, primaryLanguage.toModel(), secondaryLanguage.toModel(), imageUrl)
    }

    private fun LanguageLocalModel.toModel(): LanguageModel = LanguageModel(id, name)

    private fun LanguageModel.toDataSourceModel() = LanguageLocalModel(id, name)

    private fun WordGroupModel.toDataSourceModel() = WordGroupLocalModel(
        id, primaryLanguage.toDataSourceModel(),
        secondaryLanguage.toDataSourceModel(), imageUrl
    )
}