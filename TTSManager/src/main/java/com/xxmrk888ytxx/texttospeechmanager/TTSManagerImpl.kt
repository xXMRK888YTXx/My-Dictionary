package com.xxmrk888ytxx.texttospeechmanager

import android.content.Context
import android.speech.tts.TextToSpeech

internal class TTSManagerImpl(
    private val context: Context,
    private val onInitSuccessful:() -> Unit,
    private val onInitFailed: () -> Unit
) : TTSManager,TextToSpeech.OnInitListener {

    private val tts by lazy {
        TextToSpeech(context,this)
    }

    private var isInitSuccessful:Boolean? = null

    override fun init() {
        if(isInitSuccessful == null) {
            tts
        }
    }

    override fun speck(text: String,utteranceId:String) : Result<Unit> {
        if(isInitSuccessful != true) return Result.failure(TTSFailedInitException())

        return try {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,utteranceId)

            Result.success(Unit)
        }catch (e:Exception) {
            Result.failure(e)
        }

    }

    override fun onInit(status: Int) {
        isInitSuccessful = status == TextToSpeech.SUCCESS

        if (isInitSuccessful == true) {
            onInitSuccessful()
        } else {
            onInitFailed()
        }
    }
}