package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import com.xxmrk888ytxx.translator.Translator
import com.xxmrk888ytxx.translatorscreen.contract.ProvideTranslatorContract
import javax.inject.Inject

class ProvideTranslatorContractImpl @Inject constructor(
    private val translator: Translator
) : ProvideTranslatorContract {


    override fun translate(
        text: String,
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onTranslated: (String) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        translator.translate(text, sourceLanguageCode, targetLanguageCode, onTranslated, onError)
    }

    override fun isModelDownloaded(
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onResult: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        translator.isModelDownloaded(sourceLanguageCode, targetLanguageCode, onResult, onError)
    }

    override fun downloadModel(
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        translator.downloadModel(sourceLanguageCode, targetLanguageCode, onSuccess, onError)
    }
}