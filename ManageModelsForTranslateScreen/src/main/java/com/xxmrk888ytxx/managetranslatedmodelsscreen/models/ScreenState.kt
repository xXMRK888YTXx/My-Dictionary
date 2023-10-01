package com.xxmrk888ytxx.managetranslatedmodelsscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val translateModelList:ImmutableList<TranslateModel> = persistentListOf(),
    val isLoading:Boolean = true
)
