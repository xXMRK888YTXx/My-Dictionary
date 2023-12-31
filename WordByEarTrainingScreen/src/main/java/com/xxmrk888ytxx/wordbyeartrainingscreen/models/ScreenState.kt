package com.xxmrk888ytxx.wordbyeartrainingscreen.models

import com.xxmrk888ytxx.basetrainingcomponents.models.Question
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingProgress
import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val trainingParams: TrainingParams = TrainingParams(),
    val screenType: ScreenType = ScreenType.CONFIGURATION,
    val availableWordGroup: ImmutableList<WordGroup> = persistentListOf(),
    val questions:ImmutableList<Question> = persistentListOf(),
    val trainingProgress: TrainingProgress = TrainingProgress(),
    val isExitDialogVisible:Boolean = false
)
