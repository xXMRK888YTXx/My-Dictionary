package com.xxmrk888ytxx.viewgroupwordsscreen.models

import kotlinx.collections.immutable.ImmutableList

sealed class ScreenState {

    object EmptyState : ScreenState()

    data class ViewWords(val words:ImmutableList<Word>) : ScreenState()
}
