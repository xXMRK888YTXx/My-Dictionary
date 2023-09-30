package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import com.xxmrk888ytxx.translator.Translator
import com.xxmrk888ytxx.translatorscreen.contract.ProvideTranslatorContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProvideTranslatorContractImpl @Inject constructor(
    private val translator: Translator
) : ProvideTranslatorContract {

    override fun translate(
        text: String,
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ): Flow<Result<String>> {
        return translator.translate(text, sourceLanguageCode, targetLanguageCode)
    }

    override fun isModelDownloaded(
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ): Flow<Result<Boolean>> {
        return translator.isModelDownloaded(sourceLanguageCode, targetLanguageCode)
    }

    override fun downloadModel(
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ): Flow<Result<Unit>> {
        return translator.downloadModel(sourceLanguageCode, targetLanguageCode)
    }


}