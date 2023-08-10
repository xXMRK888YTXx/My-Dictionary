package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.cryptomanager.CryptoManager
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
interface CryptoManagerModule {

    companion object {
        @Provides
        @AppScope
        fun provideCryptoManager() : CryptoManager {
            return CryptoManager.create()
        }
    }
}