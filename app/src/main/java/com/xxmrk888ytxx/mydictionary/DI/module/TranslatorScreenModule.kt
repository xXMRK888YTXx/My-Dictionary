package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.FastSaveWordInWordGroupContractImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ManagerCurrentLanguageForTranslateImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ManagerCurrentOriginalWordLanguageImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ProvideTranslatorContractImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ProvideWordGroupInfoImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.TextToSpeechContractImpl
import com.xxmrk888ytxx.translatorscreen.contract.FastSaveWordInWordGroupContract
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentLanguageForTranslate
import com.xxmrk888ytxx.translatorscreen.contract.ManagerCurrentOriginalWordLanguage
import com.xxmrk888ytxx.translatorscreen.contract.ProvideTranslatorContract
import com.xxmrk888ytxx.translatorscreen.contract.ProvideWordGroupInfo
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

    @Binds
    fun bindProvideWordGroupInfo(
        provideWordGroupInfoImpl: ProvideWordGroupInfoImpl
    ) : ProvideWordGroupInfo

    @Binds
    fun bindFastSaveWordInWordGroupContract(
        fastSaveWordInWordGroupContractImpl: FastSaveWordInWordGroupContractImpl
    ) : FastSaveWordInWordGroupContract
}