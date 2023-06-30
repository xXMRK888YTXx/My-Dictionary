package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository

import com.xxmrk888ytxx.database.dataSource.WordPhraseLocalDataSource.WordPhraseLocalDataSource
import com.xxmrk888ytxx.database.models.WordPhraseLocalModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordPhrasesRepositoryImpl @Inject constructor(
    private val wordPhraseLocalDataSource: WordPhraseLocalDataSource
) : WordPhrasesRepository {
    override val wordPhrasesFlowModel: Flow<List<WordPhraseModel>> = wordPhraseLocalDataSource.wordPhrasesFlow.map { list ->
        list.map { it.toModel() }
    }

    override suspend fun insertWordPhrase(wordPhraseModel: WordPhraseModel): Int {
        return wordPhraseLocalDataSource.insertWordPhrase(wordPhraseModel.toLocalModel())
    }

    override suspend fun removeWordPhrase(id: Int) {
        wordPhraseLocalDataSource.removeWordPhrase(id)
    }

    private fun WordPhraseModel.toLocalModel() : WordPhraseLocalModel {
        return WordPhraseLocalModel(id, wordId, phraseText, translateText)
    }

    private fun WordPhraseLocalModel.toModel() : WordPhraseModel {
        return WordPhraseModel(id, wordId, phraseText, translateText)
    }
}