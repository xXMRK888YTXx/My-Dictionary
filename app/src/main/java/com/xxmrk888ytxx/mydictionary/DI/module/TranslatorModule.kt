package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.translator.Translator
import dagger.Module
import dagger.Provides

@Module
interface TranslatorModule {

    companion object {
        @Provides
        fun provideTranslator() : Translator {
            return Translator.create()
        }
    }
}