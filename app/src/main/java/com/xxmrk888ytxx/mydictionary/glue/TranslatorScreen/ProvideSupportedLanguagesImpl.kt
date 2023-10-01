package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider.SupportedLanguagesForTranslateProvider
import com.xxmrk888ytxx.translatorscreen.contract.ProvideSupportedLanguages
import com.xxmrk888ytxx.translatorscreen.models.SupportedLanguage
import javax.inject.Inject

class ProvideSupportedLanguagesImpl @Inject constructor(
    private val supportedLanguagesForTranslateProvider: SupportedLanguagesForTranslateProvider
) : ProvideSupportedLanguages {

    override val supportedLanguages: List<SupportedLanguage> = supportedLanguagesForTranslateProvider.supportedLanguageForTranslate.map {
        SupportedLanguage(it.code,it.name)
    }
}