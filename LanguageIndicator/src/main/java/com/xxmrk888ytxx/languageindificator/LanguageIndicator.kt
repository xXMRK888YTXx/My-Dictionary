package com.xxmrk888ytxx.languageindificator

import kotlinx.coroutines.CoroutineScope
import kotlin.jvm.Throws

interface LanguageIndicator {

    @Throws(LanguageNotIdentifiedException::class)
    suspend fun getLanguageCodeFromText(text:String) : String

    companion object {
        fun create(
            taskExecuteScope: CoroutineScope
        ) : LanguageIndicator {
            return LanguageIndicatorImpl(taskExecuteScope)
        }
    }
}