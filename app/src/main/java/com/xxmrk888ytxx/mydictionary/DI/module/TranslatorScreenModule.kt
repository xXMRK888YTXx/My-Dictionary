package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.TextToSpeechContractImpl
import com.xxmrk888ytxx.translatorscreen.contract.TextToSpeechContract
import dagger.Binds
import dagger.Module

@Module
interface TranslatorScreenModule {

    @Binds
    fun bindTextToSpeechContract(
        textToSpeechContractImpl: TextToSpeechContractImpl
    ): TextToSpeechContract
}