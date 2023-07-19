package com.xxmrk888ytxx.wordbyeartrainingscreen.contract

import com.xxmrk888ytxx.basetrainingcomponents.models.Question
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope

interface GenerateQuestionForTrainingContract {

    suspend fun generateQuestion(
        scope:CoroutineScope,
        trainingParams: TrainingParams
    ) : ImmutableList<Question>
}