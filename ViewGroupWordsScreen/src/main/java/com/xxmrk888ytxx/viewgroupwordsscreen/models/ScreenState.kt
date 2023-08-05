package com.xxmrk888ytxx.viewgroupwordsscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val wordGroupInfo:WordGroupInfo = WordGroupInfo("","",""),
    val words:ImmutableList<Word> = persistentListOf(),
    val wordOptionDialogState:WordOptionDialogState = WordOptionDialogState.Hidden,
    val searchState: SearchState = SearchState.Disabled
)
