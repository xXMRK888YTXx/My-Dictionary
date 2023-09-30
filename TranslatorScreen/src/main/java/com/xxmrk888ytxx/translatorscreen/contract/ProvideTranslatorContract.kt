package com.xxmrk888ytxx.translatorscreen.contract

import kotlinx.coroutines.flow.Flow

interface ProvideTranslatorContract {

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
}