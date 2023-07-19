package com.xxmrk888ytxx.mydictionary.glue.WordByEarTrainingScreen

import com.xxmrk888ytxx.basetrainingcomponents.models.Question
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase.GenerateQuestionForTrainingUseCase
import com.xxmrk888ytxx.wordbyeartrainingscreen.contract.GenerateQuestionForTrainingContract
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GenerateQuestionForTrainingContractImpl @Inject constructor(
    private val generateQuestionForTrainingUseCase: GenerateQuestionForTrainingUseCase
) : GenerateQuestionForTrainingContract {

    override suspend fun generateQuestion(
        scope: CoroutineScope,
        trainingParams: TrainingParams,
    ): ImmutableList<Question> {
        return generateQuestionForTrainingUseCase.generateQuestion(scope, trainingParams)
    }
}