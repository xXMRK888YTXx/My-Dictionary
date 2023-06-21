package com.xxmrk888ytxx.createwordgroupscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ScreenState(
    val newGroupName:String = "",
    val imageGroupUrl:String? = null,
    val languages: ImmutableList<Language> = persistentListOf(),
    val selectedPrimaryLanguage: Language? = null,
    val selectedSecondaryLanguage: Language? = null
)
