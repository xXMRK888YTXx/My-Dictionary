package com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts

import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideWordGroupsContract {

    val wordGroups: Flow<ImmutableList<WordGroup>>
}