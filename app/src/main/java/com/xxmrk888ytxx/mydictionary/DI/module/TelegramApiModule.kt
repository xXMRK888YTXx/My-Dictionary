package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.telegramapi.TelegramApiFactory
import dagger.Module
import dagger.Provides

@Module
class TelegramApiModule {

    companion object {
        @Provides
        @AppScope
        fun provideTelegramApiFactory() : TelegramApiFactory {
            return TelegramApiFactory.ktorFactory()
        }
    }
}