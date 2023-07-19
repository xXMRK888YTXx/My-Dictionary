package com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen

import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.GenerateQuestionForTrainingContract
import com.xxmrk888ytxx.basetrainingcomponents.models.Question
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

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