package com.xxmrk888ytxx.mydictionary

import android.app.Application
import com.xxmrk888ytxx.coredeps.DepsProvider.DepsProvider
import com.xxmrk888ytxx.coredeps.Exceptions.DepsProviderNotFoundDeps
import com.xxmrk888ytxx.mydictionary.DI.AppComponent
import com.xxmrk888ytxx.mydictionary.DI.DaggerAppComponent
import kotlin.reflect.KClass

class App : Application(), DepsProvider {

    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.billingManager.connectToGooglePlay()
        appComponent.billingManager.restorePurchases()
    }

    private val depsMap: Map<KClass<*>, () -> Any> by lazy {
        mapOf(
            appComponent.logger.toProvidedDeps(),
            appComponent.backupMaker.toProvidedDeps(),
            appComponent.isBackupNeededChecker.toProvidedDeps()
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <DEPS : Any> provide(classType: KClass<DEPS>): DEPS {
        val value = depsMap[classType]?.invoke()

        if(value != null)
            return value as DEPS

        throw DepsProviderNotFoundDeps("DepsProvider cant provide ${classType.simpleName}")
    }

    private inline fun <reified T : Any> T.toProvidedDeps() : Pair<KClass<*>,() -> Any> {
        return Pair(T::class) { this }
    }

    private inline fun <reified TYPE : Any, LAZY : dagger.Lazy<TYPE>> LAZY.toProvidedDeps() : Pair<KClass<*>,() -> Any> {
        return Pair(TYPE::class) { this.get() }
    }
}