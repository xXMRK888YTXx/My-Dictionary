package com.xxmrk888ytxx.wordgroupscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val wordList:ImmutableList<WordGroup> = persistentListOf(),
    val wordGroupDialogOptionState: WordGroupDialogOptionState = WordGroupDialogOptionState.Hidden
)