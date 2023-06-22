package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.data.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.LanguageRepository.LanguageRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl) : LanguageRepository
}