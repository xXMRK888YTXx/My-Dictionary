package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.admobmanager.AdMobManager
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class AdmobModule {

    @Provides
    @AppScope
    fun providesAdMobManager(context: Context) : AdMobManager {
        return AdMobManager.create(context)
    }
}