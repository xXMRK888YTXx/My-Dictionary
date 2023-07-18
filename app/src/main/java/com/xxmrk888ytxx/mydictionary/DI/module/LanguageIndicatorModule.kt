package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.languageindificator.LanguageIndicator
import com.xxmrk888ytxx.mydictionary.DI.Qualifiers.ScopeForLanguageIndicatorQualifier
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
interface LanguageIndicatorModule {

    companion object {

        @AppScope
        @Provides
        @ScopeForLanguageIndicatorQualifier
        fun provideScopeForLanguageIndicator() : CoroutineScope {
            return CoroutineScope(Dispatchers.Default + SupervisorJob())
        }

        @AppScope
        @Provides
        fun provideLanguageIndicator(
            @ScopeForLanguageIndicatorQualifier scope : CoroutineScope
        ) : LanguageIndicator {
            return LanguageIndicator.create(scope)
        }
    }
}