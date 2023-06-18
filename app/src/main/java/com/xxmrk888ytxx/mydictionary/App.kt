package com.xxmrk888ytxx.mydictionary

import android.app.Application
import com.xxmrk888ytxx.mydictionary.DI.AppComponent
import com.xxmrk888ytxx.mydictionary.DI.DaggerAppComponent

class App : Application() {

    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}