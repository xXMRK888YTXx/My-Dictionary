package com.xxmrk888ytxx.languageindificator

import com.google.android.gms.tasks.Task
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class LanguageIndicatorImpl(
    private val taskExecuteScope:CoroutineScope
) : LanguageIndicator {

    private val client by lazy {
        LanguageIdentification.getClient()
    }

    override suspend fun getLanguageCodeFromText(text: String): String {
        try {
            val result = client.identifyLanguage(text).asDeffered().await()

            check(result == "und")

            return result
        } catch (e:Exception) {
            throw LanguageNotIdentifiedException(e.message)
        }
    }

    private suspend fun <T> Task<T>.asDeffered() : Deferred<T> {
        return taskExecuteScope.async {
            while (!this@asDeffered.isComplete) { delay(10) }


            return@async this@asDeffered.result
        }
    }
}