package com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase

import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.basetrainingcomponents.models.Question
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope

interface GenerateQuestionForTrainingUseCase {

    suspend fun generateQuestion(
        scope: CoroutineScope,
        trainingParams: TrainingParams
    ) : ImmutableList<Question>
}