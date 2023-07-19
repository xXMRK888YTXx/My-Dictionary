package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindTextToSpeechUseCase(
        TextToSpeechUseCaseImpl: TextToSpeechUseCaseImpl
    ) : TextToSpeechUseCase
}