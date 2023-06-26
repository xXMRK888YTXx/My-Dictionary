package com.xxmrk888ytxx.wordgroupscreen.models

import kotlinx.collections.immutable.ImmutableList

sealed class ScreenState {

    object EmptyWordGroupState : ScreenState()

    data class WordList(val wordList:ImmutableList<WordGroup>) : ScreenState()
}