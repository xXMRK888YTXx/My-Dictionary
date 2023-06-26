package com.xxmrk888ytxx.mydictionary.DI

import android.content.Context
import com.xxmrk888ytxx.mydictionary.DI.module.CoreModule
import com.xxmrk888ytxx.mydictionary.DI.module.CreateWordGroupScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.DataModule
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.mydictionary.DI.module.DatabaseModule
import com.xxmrk888ytxx.mydictionary.DI.module.ViewGroupWordsScreenModule
import com.xxmrk888ytxx.mydictionary.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        CoreModule::class,
        CreateWordGroupScreenModule::class,
        DatabaseModule::class,
        DataModule::class,
        ViewGroupWordsScreenModule::class
    ]
)
@AppScope
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context:Context) : AppComponent
    }
}