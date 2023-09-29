package com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen

import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider.SupportedLanguagesForTranslateProvider
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentLanguageForTranslate
import com.xxmrk888ytxx.translatorscreen.models.SupportedLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ManagerCurrentLanguageForTranslateImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
    private val supportedLanguagesForTranslateProvider: SupportedLanguagesForTranslateProvider,
) : ManagerCurrentLanguageForTranslate {

    private val preferenceKey = stringPreferencesKey("ManagerCurrentLanguageForTranslate")

    private val defaultValue = supportedLanguagesForTranslateProvider.defaultLanguageForTranslate.code

    override val currentLanguage: Flow<SupportedLanguage> = preferencesStorage.getProperty(
        preferenceKey,defaultValue
    ).map { data ->
        supportedLanguagesForTranslateProvider.supportedLanguageForTranslateMap[data] ?: supportedLanguagesForTranslateProvider.defaultLanguageForTranslate
    }.map {
        SupportedLanguage(it.code,it.name)
    }

    override suspend fun setupLanguage(supportedLanguage: SupportedLanguage) {
        preferencesStorage.writeProperty(preferenceKey,supportedLanguage.code)
    }
}