package com.xxmrk888ytxx.database.dataSource.WordPhraseLocalDataSource

import com.xxmrk888ytxx.database.dao.WordPhraseDao
import com.xxmrk888ytxx.database.entityes.WordPhraseEntity
import com.xxmrk888ytxx.database.models.WordPhraseLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class WordPhraseLocalDataSourceImpl(
    private val wordPhraseDao: WordPhraseDao
) : WordPhraseLocalDataSource {
    override val wordPhrasesFlow: Flow<List<WordPhraseLocalModel>> = wordPhraseDao.wordPhrasesFlow.map { list ->
        list.map { it.toLocalModel() }
    }

    private val mutex = Mutex()

    override suspend fun insertWordPhrase(wordPhraseLocalModel: WordPhraseLocalModel) = withContext(Dispatchers.IO) {
        return@withContext mutex.withLock {
            wordPhraseDao.insertWordPhrase(wordPhraseLocalModel.toEntity())
        }
    }

    override suspend fun getPhrasesByWordId(wordId: Int): List<WordPhraseLocalModel> = withContext(Dispatchers.IO) {
        return@withContext wordPhraseDao.getPhrasesByWordId(wordId).map { it.toLocalModel() }
    }

    override suspend fun removeWordPhrase(id: Int) = withContext(Dispatchers.IO) {
        mutex.withLock {
            wordPhraseDao.removeWordPhrase(id)
        }
    }

    private fun WordPhraseEntity.toLocalModel() : WordPhraseLocalModel {
        return WordPhraseLocalModel(id, wordId, phraseText, translateText)
    }

    private fun WordPhraseLocalModel.toEntity() : WordPhraseEntity {
        return WordPhraseEntity(id, wordId, phraseText, translateText)
    }
}