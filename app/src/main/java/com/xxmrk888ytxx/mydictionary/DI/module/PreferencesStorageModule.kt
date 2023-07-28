package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class PreferencesStorageModule {

    companion object {

        @Provides
        @AppScope
        fun providesPreferencesStorage(context: Context) : PreferencesStorage {
            return PreferencesStorage.Factory().create("PreferencesStorage",context)
        }
    }
}