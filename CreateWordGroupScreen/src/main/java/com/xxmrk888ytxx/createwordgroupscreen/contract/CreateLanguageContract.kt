package com.xxmrk888ytxx.createwordgroupscreen.contract

import com.xxmrk888ytxx.createwordgroupscreen.models.Language

/**
 * [Ru]
 * Контракт на создание нового языка
 */

/**
 * [Eu]
 * Contract for the creation of a new language
 */
interface CreateLanguageContract {

    suspend fun newLanguage(newLanguageName:String)
}