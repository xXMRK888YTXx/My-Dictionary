package com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen

import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.GenerateQuestionForTrainingContract
import com.xxmrk888ytxx.basetrainingcomponents.models.Question
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase.GenerateQuestionForTrainingUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GenerateQuestionForTrainingContractImpl @Inject constructor(
    private val generateQuestionForTrainingUseCase: GenerateQuestionForTrainingUseCase
) : GenerateQuestionForTrainingContract {

    override suspend fun generateQuestion(
        scope: CoroutineScope,
        trainingParams: TrainingParams,
    ): ImmutableList<Question>  {
        return generateQuestionForTrainingUseCase.generateQuestion(scope, trainingParams)
    }


}