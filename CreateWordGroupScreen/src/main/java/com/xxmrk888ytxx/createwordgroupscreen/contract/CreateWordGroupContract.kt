package com.xxmrk888ytxx.createwordgroupscreen.contract

import com.xxmrk888ytxx.createwordgroupscreen.models.Language

/**
 * [Ru]
 * Контракт на создание нововой группы слов
 */

/**
 * [Ru]
 * Contract to create a new group of words
 */
interface CreateWordGroupContract {

    suspend fun createWordGroup(
        groupName: String,
        primaryLanguage: Language,
        secondLanguage: Language,
        imageUrl: String?,
    )
}