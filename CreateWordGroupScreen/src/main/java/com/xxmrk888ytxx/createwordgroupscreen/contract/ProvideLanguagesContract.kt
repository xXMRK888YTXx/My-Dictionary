package com.xxmrk888ytxx.createwordgroupscreen.contract

import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

/**
 * [Ru]
 * Контракт для предоставления языков для групп слов
 */

/**
 * [En]
 * Contract for the provision of languages for groups of words
 */
interface ProvideLanguagesContract {

    val languages: Flow<ImmutableList<Language>>
}