package com.xxmrk888ytxx.languageindificator

import com.google.android.gms.tasks.Task
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class LanguageDeterminantImpl(
    private val taskExecuteScope:CoroutineScope
) : LanguageDeterminant {


    override suspend fun getLanguageCodeFromText(text: String): String {
        try {
            val result = LanguageIdentification.getClient().identifyLanguage(text).asDeffered().await()

            if(result == "und") {
                throw LanguageNotIdentifiedException()
            }

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