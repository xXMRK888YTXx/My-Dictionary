package com.xxmrk888ytxx.texttospeechmanager

import android.content.Context

interface TTSManager {

    fun init()

    fun speck(text:String,utteranceId:String = "Default") : Result<Unit>

    companion object {
        fun create(context: Context,onInitSuccessful:() -> Unit = {}, onInitFailed: () -> Unit = {}) : TTSManager {
            return TTSManagerImpl(context, onInitSuccessful, onInitFailed)
        }
    }
}