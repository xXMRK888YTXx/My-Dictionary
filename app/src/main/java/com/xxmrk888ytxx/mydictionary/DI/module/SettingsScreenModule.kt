package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.ProvideApplicationVersionImpl
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersion
import dagger.Binds
import dagger.Module

@Module
interface SettingsScreenModule {

    @Binds
    fun bindProvideApplicationVersion(
        ProvideApplicationVersionImpl: ProvideApplicationVersionImpl
    ): ProvideApplicationVersion
}