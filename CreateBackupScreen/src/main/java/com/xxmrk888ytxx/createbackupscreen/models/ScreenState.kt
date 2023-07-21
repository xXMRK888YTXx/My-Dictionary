package com.xxmrk888ytxx.createbackupscreen.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

data class ScreenState(
    val availableWordGroups: ImmutableList<WordGroup> = persistentListOf(),
    val selectedWordGroupId:ImmutableSet<Int> = persistentSetOf()
)
