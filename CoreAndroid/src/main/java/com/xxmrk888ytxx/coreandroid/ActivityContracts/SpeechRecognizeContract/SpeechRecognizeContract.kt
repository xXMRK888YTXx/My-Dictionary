package com.xxmrk888ytxx.coreandroid.ActivityContracts.SpeechRecognizeContract

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract


//Send intent to system for recognize speech
class SpeechRecognizeContract : ActivityResultContract<String, String?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {

            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            putExtra(RecognizerIntent.EXTRA_LANGUAGE, input)

            putExtra(RecognizerIntent.EXTRA_PROMPT,"Please, speck now")
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        val result = intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

        return try {
            result?.get(0) ?: return null
        }catch (e:IndexOutOfBoundsException) { null }
    }

}