package com.xxmrk888ytxx.wordtranslatetrainingscreen.models

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

data class TrainingParams(
    val questionCount:Int = 10,
    val isUsePhrases:Boolean = false,
    val selectedWordGroupsId:ImmutableSet<Int> = persistentSetOf()
)