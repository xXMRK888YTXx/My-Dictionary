package com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordPhraseModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.GenerateQuestionForTrainingContract
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.Question
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
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
    private val generateQuestionForTrainingContract: GenerateQuestionForTrainingContract
) : GenerateQuestionForTrainingContract {

    override suspend fun generateQuestion(
        scope: CoroutineScope,
        trainingParams: TrainingParams,
    ): ImmutableList<Question>  {
        return generateQuestionForTrainingContract.generateQuestion(scope, trainingParams)
    }


}