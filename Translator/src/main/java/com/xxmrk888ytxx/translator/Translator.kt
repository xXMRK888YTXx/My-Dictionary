package com.xxmrk888ytxx.translator

interface Translator {

    fun translate(
        text:String,
        sourceLanguageCode:String,
        targetLanguageCode:String,
        onTranslated:(String) -> Unit,
        onError:(Throwable) -> Unit
    )

    fun isModelDownloaded(
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onResult:(Boolean) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun downloadModel(
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onSuccess:() -> Unit,
        onError: (Throwable) -> Unit
    )

    companion object {
        fun create() : Translator = TranslatorImpl()
    }
}