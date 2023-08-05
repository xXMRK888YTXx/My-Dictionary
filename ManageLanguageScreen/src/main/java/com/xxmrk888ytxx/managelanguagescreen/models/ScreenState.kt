package com.xxmrk888ytxx.managelanguagescreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val languageList:ImmutableList<Language> = persistentListOf(),
    val createLanguageDialogState:CreateLanguageDialogState = CreateLanguageDialogState.Hidden,
    val isRemovingInProcess:Boolean = false
)
