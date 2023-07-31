package com.xxmrk888ytxx.languageindificator

import kotlinx.coroutines.CoroutineScope
import kotlin.jvm.Throws

/**
 * [Ru]
 * Интерфейс для определения языка строки
 */

/**
 * [En]
 * Interface for determining the string language
 */
interface LanguageDeterminant {

    /**
     * [Ru]
     * Возвращает код языка строки,
     * если определить язык невозможно, то выбрасывает исключение [LanguageNotIdentifiedException]
     */

    /**
     * [En]
     * Return language code of string,
     * if determinate language is impossible, it will be throw exception [LanguageNotIdentifiedException]
     */
    @Throws(LanguageNotIdentifiedException::class)
    suspend fun getLanguageCodeFromText(text:String) : String

    companion object {
        fun create(
            taskExecuteScope: CoroutineScope
        ) : LanguageDeterminant {
            return LanguageDeterminantImpl(taskExecuteScope)
        }
    }
}