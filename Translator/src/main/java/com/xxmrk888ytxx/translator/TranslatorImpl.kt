package com.xxmrk888ytxx.translator

import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update

internal class TranslatorImpl : Translator {


    private val _downloadedModels = MutableStateFlow<List<TranslationModel>>(emptyList())

    override val downloadedModel: Flow<List<TranslationModel>> = _downloadedModels.asStateFlow()

        override fun translate(
        text: String,
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ): Flow<Result<String>> {
        return callbackFlow {
            val option = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(targetLanguageCode)
                .build()

            val translatorModel = Translation.getClient(option)

            translatorModel.translate(text)
                .addOnSuccessListener {
                    trySendBlocking(Result.success(it))
                }
                .addOnFailureListener {
                    trySendBlocking(Result.failure(it))
                }
                .addOnCompleteListener {
                    close()
                }

            awaitClose {
                translatorModel.close()
            }
        }
    }

    override fun isModelDownloaded(
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ): Flow<Result<Boolean>> {
        return callbackFlow {
            val modelManager = RemoteModelManager.getInstance()

            modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
                .addOnSuccessListener { models ->
                    trySendBlocking(Result.success(models.map { it.language }
                        .containsAll(listOf(sourceLanguageCode, targetLanguageCode))))
                }
                .addOnFailureListener {
                    trySendBlocking(Result.failure(it))
                }
                .addOnCompleteListener {
                    close()
                }

            awaitClose { }
        }
    }

    override fun downloadModel(
        sourceLanguageCode: String,
        targetLanguageCode: String,
    ): Flow<Result<Unit>> {
        return callbackFlow {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguageCode)
                .setTargetLanguage(targetLanguageCode)
                .build()

            val translator = Translation.getClient(options)

            translator.downloadModelIfNeeded()
                .addOnSuccessListener {
                    trySendBlocking(Result.success(Unit))
                    updateDownloadedModels()
                }
                .addOnFailureListener {
                    trySendBlocking(Result.failure(it))
                }
                .addOnCompleteListener {
                    close()
                }

            awaitClose { translator.close() }
        }
    }

    override fun removeModel(languageCode: String): Flow<Result<Unit>> {
        return callbackFlow {
            val modelManager = RemoteModelManager.getInstance()

            modelManager.deleteDownloadedModel(TranslateRemoteModel.Builder(languageCode).build())
                .addOnSuccessListener {
                    trySendBlocking(Result.success(Unit))
                    updateDownloadedModels()
                }
                .addOnFailureListener {
                    trySendBlocking(Result.failure(it))
                }
                .addOnCompleteListener {
                    close()
                }

            awaitClose {  }
        }
    }

    private fun updateDownloadedModels() {
        val modelManager = RemoteModelManager.getInstance()

        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                _downloadedModels.update { models.map { TranslationModel(it.language) } }
            }
    }

    init {
        updateDownloadedModels()
    }





}