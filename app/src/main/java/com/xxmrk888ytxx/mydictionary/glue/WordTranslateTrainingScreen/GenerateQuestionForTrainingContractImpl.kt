package com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.GenerateQuestionForTrainingContract
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.Question
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingParams
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class GenerateQuestionForTrainingContractImpl @Inject constructor(
    private val wordRepository: WordRepository,
    private val wordPhrasesRepository: WordPhrasesRepository,
) : GenerateQuestionForTrainingContract {

    override suspend fun generateQuestion(
        scope: CoroutineScope,
        trainingParams: TrainingParams,
    ): ImmutableList<Question> = withContext(Dispatchers.Default) {

        val question = provideQuestion(scope, trainingParams)

        val finalList = mutableListOf<Question>()

        val usedIndexes = hashSetOf<Int>()

        while (finalList.size < trainingParams.questionCount && question.size != usedIndexes.size) {
            val randomIndex = generateRandomId(usedIndexes,question.lastIndex)

            usedIndexes.add(randomIndex)

            finalList.add(question[randomIndex])
        }

        return@withContext finalList.toImmutableList()
    }

    private fun generateRandomId(usedIndexes:Set<Int>,lastIndexOfList:Int) : Int {
        var randomIndex = Random(UUID.randomUUID().toString().hashCode()).nextInt(0,lastIndexOfList)

        while (usedIndexes.contains(randomIndex)) {
            randomIndex = if(randomIndex == lastIndexOfList) 0 else randomIndex + 1
        }

        return randomIndex
    }

    private suspend fun provideQuestion(
        scope: CoroutineScope,
        trainingParams: TrainingParams,
    ) : List<Question> {
        val words = scope.async(Dispatchers.IO) {
            val finalList = mutableListOf<Question>()

            trainingParams.selectedWordGroupsId.forEach { wordGroupId ->
                wordRepository.getWordsByWordGroupId(wordGroupId).first().forEach {
                    finalList.add(it.toQuestion())
                }
            }

            return@async finalList
        }

        val phrases = scope.async(Dispatchers.IO) {
            if (!trainingParams.isUsePhrases) return@async emptyList()

            val finalList = mutableListOf<Question>()

            trainingParams.selectedWordGroupsId.forEach { wordGroupId ->
                wordPhrasesRepository.getPhrasesByWordId(wordGroupId).forEach {
                    finalList.add(it.toQuestion())
                }
            }

            return@async finalList
        }

        return merge(words.await(),phrases.await())
    }

    private fun WordModel.toQuestion(): Question {
        return Question(this.wordText, this.translateText)
    }

    private fun WordPhraseModel.toQuestion(): Question {
        return Question(phraseText, translateText)
    }

    private fun merge(l1:List<Question>,l2:List<Question>) : List<Question> {
        val finalList = mutableListOf<Question>()

        l1.forEach { finalList.add(it) }
        l2.forEach { finalList.add(it) }

        return finalList
    }
}