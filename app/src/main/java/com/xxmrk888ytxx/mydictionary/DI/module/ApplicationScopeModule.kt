package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.DI.Qualifiers.ApplicationScopeQualifier
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
class ApplicationScopeModule {

    @Provides
    @AppScope
    @ApplicationScopeQualifier
    fun provideApplicationScope() : CoroutineScope {
        return CoroutineScope(Dispatchers.IO + SupervisorJob())
    }
}