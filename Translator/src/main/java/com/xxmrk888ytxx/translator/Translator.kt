package com.xxmrk888ytxx.translator

import kotlinx.coroutines.flow.Flow

interface Translator {

    fun translate(
        text:String,
        sourceLanguageCode:String,
        targetLanguageCode:String
    ) : Flow<Result<String>>

    fun isModelDownloaded(
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ) : Flow<Result<Boolean>>

    fun downloadModel(
        sourceLanguageCode: String,
        targetLanguageCode: String
    ) : Flow<Result<Unit>>

    companion object {
        fun create() : Translator = TranslatorImpl()
    }
}