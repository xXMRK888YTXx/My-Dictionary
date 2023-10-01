package com.xxmrk888ytxx.translatorscreen.contract

import com.xxmrk888ytxx.translatorscreen.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideWordGroupInfo {

    val wordGroups:Flow<ImmutableList<WordGroup>>
}