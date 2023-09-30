package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ManagerCurrentLanguageForTranslateImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ManagerCurrentOriginalWordLanguageImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ProvideTranslatorContractImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.TextToSpeechContractImpl
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentLanguageForTranslate
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentOriginalWordLanguage
import com.xxmrk888ytxx.translatorscreen.contract.ProvideTranslatorContract
import com.xxmrk888ytxx.translatorscreen.contract.TextToSpeechContract
import dagger.Binds
import dagger.Module

@Module
interface TranslatorScreenModule {

    @Binds
    fun bindTextToSpeechContract(
        textToSpeechContractImpl: TextToSpeechContractImpl
    ): TextToSpeechContract

    @Binds
    fun bindManagerCurrentLanguageForTranslate(
        managerCurrentLanguageForTranslateImpl: ManagerCurrentLanguageForTranslateImpl
    ) : ManagerCurrentLanguageForTranslate

    @Binds
    fun bindManagerCurrentOriginalWordLanguage(
        managerCurrentOriginalWordLanguageImpl: ManagerCurrentOriginalWordLanguageImpl
    ) : ManagerCurrentOriginalWordLanguage

    @Binds
    fun bindProvideTranslatorContract(
        provideTranslatorContractImpl: ProvideTranslatorContractImpl
    ) : ProvideTranslatorContract
}