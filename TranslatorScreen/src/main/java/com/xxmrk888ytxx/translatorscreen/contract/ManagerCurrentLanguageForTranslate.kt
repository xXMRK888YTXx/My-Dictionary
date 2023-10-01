package com.xxmrk888ytxx.translatorscreen.contract

import com.xxmrk888ytxx.translatorscreen.models.SupportedLanguage
import kotlinx.coroutines.flow.Flow

interface ManagerCurrentLanguageForTranslate {

    val currentLanguage: Flow<SupportedLanguage>

    suspend fun setupLanguage(supportedLanguage: SupportedLanguage)
}