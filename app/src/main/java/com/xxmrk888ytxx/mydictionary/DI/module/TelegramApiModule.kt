package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.backupworker.BackupWorkerManager
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.telegramapi.TelegramApiFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface TelegramApiModule {

    companion object {
        @Provides
        @AppScope
        fun provideTelegramApiFactory() : TelegramApiFactory {
            return TelegramApiFactory.ktorFactory()
        }
    }
}