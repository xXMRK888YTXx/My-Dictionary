package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val trainingParams: TrainingParams = TrainingParams(),
    val screenType: ScreenType = ScreenType.CONFIGURATION,
    val availableWordGroup:ImmutableList<WordGroup> = persistentListOf(),
    val trainingProgress: TrainingProgress = TrainingProgress(),
    val question:ImmutableList<Question> = persistentListOf()
)
