package com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider

interface SupportedLanguagesForTranslateProvider {

    val supportedLanguageForTranslate:List<SupportedLanguageForTranslate>

    val supportedLanguageForTranslateMap:Map<String,SupportedLanguageForTranslate>
}