package com.xxmrk888ytxx.translator

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModel
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

internal class TranslatorImpl : Translator {
    override fun translate(
        text:String,
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onTranslated: (String) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        val option = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()

        val translatorModel = Translation.getClient(option)

        translatorModel.translate(text)
            .addOnSuccessListener {
                onTranslated(it)
            }
            .addOnFailureListener { onError(it) }
            .addOnCompleteListener {
                translatorModel.close()
            }
    }

    override fun isModelDownloaded(
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onResult: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        val modelManager = RemoteModelManager.getInstance()


        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                onResult(models.map { it.language }.containsAll(listOf(sourceLanguageCode,targetLanguageCode)))
            }
            .addOnFailureListener {
                onError(it)
            }
    }

    override fun downloadModel(
        sourceLanguageCode: String,
        targetLanguageCode: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguageCode)
            .setTargetLanguage(targetLanguageCode)
            .build()

        val translator = Translation.getClient(options)

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onError(it)
            }
            .addOnCompleteListener {
                translator.close()
            }
    }

}