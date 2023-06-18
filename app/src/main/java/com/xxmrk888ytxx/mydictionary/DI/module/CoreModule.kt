@file:OptIn(UseLoggerInterface::class)

package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.coreandroid.AndroidLogger
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.ToastManager
import com.xxmrk888ytxx.coreandroid.ToastManagerImpl
import com.xxmrk888ytxx.coreandroid.annotations.UseLoggerInterface
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @Provides
    @AppScope
    fun provideLogger() : Logger = AndroidLogger

    @Provides
    @AppScope
    fun provideToastManager(context: Context) : ToastManager {
        return ToastManagerImpl(context)
    }
}