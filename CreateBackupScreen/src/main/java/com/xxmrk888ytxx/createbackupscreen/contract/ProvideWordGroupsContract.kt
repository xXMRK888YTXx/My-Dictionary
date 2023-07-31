package com.xxmrk888ytxx.createbackupscreen.contract

import com.xxmrk888ytxx.createbackupscreen.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Контракт для предоставления групп слов
 */

/**
 * [En]
 * Contract for providing groups of words
 */
interface ProvideWordGroupsContract {

    val wordGroups: Flow<ImmutableList<WordGroup>>
}