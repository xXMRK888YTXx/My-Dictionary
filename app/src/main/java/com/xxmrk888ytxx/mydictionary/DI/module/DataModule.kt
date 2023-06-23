package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.data.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.ImageRepository.ImageRepositoryImpl
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.LanguageRepository.LanguageRepositoryImpl
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.WordGroupRepository.WordGroupRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl) : LanguageRepository

    @Binds
    fun bindWordGroupRepository(wordGroupRepository: WordGroupRepositoryImpl) : WordGroupRepository

    @Binds
    fun bindImageRepository(imageRepository: ImageRepositoryImpl) : ImageRepository
}