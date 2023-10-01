package com.xxmrk888ytxx.mydictionary.glue.ManageModelsForTranslateScreen

import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.ProvideTranslateModelsContract
import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.TranslateModel
import com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider.SupportedLanguagesForTranslateProvider
import com.xxmrk888ytxx.translator.Translator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProvideTranslateModelsContractImpl @Inject constructor(
    private val translator: Translator,
    private val supportedLanguagesForTranslateProvider: SupportedLanguagesForTranslateProvider
) : ProvideTranslateModelsContract {

    override val models: Flow<ImmutableList<TranslateModel>> = translator.downloadedModel.map { list ->
        list.map { TranslateModel(
            name = supportedLanguagesForTranslateProvider.supportedLanguageForTranslateMap[it.code]?.name,
            code = it.code
        ) }.toImmutableList()
    }
}