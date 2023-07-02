package com.xxmrk888ytxx.database.dataSource.WordsLocalDataSource

import com.xxmrk888ytxx.database.dao.WordDao
import com.xxmrk888ytxx.database.entityes.WordEntity
import com.xxmrk888ytxx.database.models.WordGroupLocalModel
import com.xxmrk888ytxx.database.models.WordLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class WordsLocalDataSourceImpl(
    private val wordDao: WordDao,
) : WordsLocalDataSource {

    private val mutex: Mutex = Mutex()

    override fun getWordsFlow(): Flow<List<WordLocalModel>> = wordDao.getWordsFlow().map { list ->
        list.map { it.toLocalModel() }
    }

    override fun getWordsByWordGroupIdFlow(wordGroupId: Int): Flow<List<WordLocalModel>> =
        wordDao.getWordsByWordGroupIdFlow(wordGroupId).map { list ->
            list.map { it.toLocalModel() }
        }

    override suspend fun getWordById(id: Int): WordLocalModel = withContext(Dispatchers.IO) {
        return@withContext wordDao.getWordById(id).toLocalModel()
    }

    override suspend fun addWord(wordGroupLocalModel: WordLocalModel): Int =
        withContext(Dispatchers.IO) {
            return@withContext mutex.withLock {
                wordDao.insertWord(wordGroupLocalModel.toEntity())

                wordDao.getLastId()
            }
        }

    override suspend fun removeWord(id: Int) = withContext(Dispatchers.IO) {
        mutex.withLock {
            wordDao.removeWord(id)
        }
    }

    private fun WordEntity.toLocalModel(): WordLocalModel {
        return WordLocalModel(id, wordGroupId, wordText, translateText, transcriptionText)
    }

    private fun WordLocalModel.toEntity(): WordEntity {
        return WordEntity(id, wordGroupId, wordText, translateText, transcriptionText)
    }
}