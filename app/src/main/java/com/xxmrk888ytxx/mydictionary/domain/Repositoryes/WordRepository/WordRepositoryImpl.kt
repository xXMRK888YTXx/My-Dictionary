package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository

import com.xxmrk888ytxx.database.dataSource.WordsLocalDataSource.WordsLocalDataSource
import com.xxmrk888ytxx.database.models.WordLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordsLocalDataSource: WordsLocalDataSource,
) : WordRepository {

    override fun getWords(): Flow<List<WordModel>> =
        wordsLocalDataSource.getWordsFlow().map { list ->
            list.map { it.toModel() }
        }

    override fun getWordsByWordGroupId(wordGroupId: Int): Flow<List<WordModel>> =
        wordsLocalDataSource.getWordsByWordGroupIdFlow(wordGroupId).map { list ->
            list.map { it.toModel() }
        }

    override suspend fun getWordById(id: Int): WordModel {
        return wordsLocalDataSource.getWordById(id).toModel()
    }

    override suspend fun addWord(wordModel: WordModel): Int {
        return wordsLocalDataSource.addWord(wordModel.toLocalModel())
    }

    override suspend fun removeWord(id: Int) {
        wordsLocalDataSource.removeWord(id)
    }

    private fun WordLocalModel.toModel(): WordModel {
        return WordModel(id, wordGroupId, wordText, translateText, transcriptionText)
    }

    private fun WordModel.toLocalModel(): WordLocalModel {
        return WordLocalModel(id, wordGroupId, wordText, translateText, transcriptionText)
    }
}