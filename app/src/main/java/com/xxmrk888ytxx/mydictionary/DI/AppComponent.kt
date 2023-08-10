package com.xxmrk888ytxx.mydictionary.DI

import android.content.Context
import com.xxmrk888ytxx.mydictionary.DI.module.AdmobModule
import com.xxmrk888ytxx.mydictionary.DI.module.ApplicationScopeModule
import com.xxmrk888ytxx.mydictionary.DI.module.ArchiverCreatorModule
import com.xxmrk888ytxx.mydictionary.DI.module.AutoBackupToTelegramScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.BackupConverterModule
import com.xxmrk888ytxx.mydictionary.DI.module.EditWordScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.CoreModule
import com.xxmrk888ytxx.mydictionary.DI.module.CreateBackupScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.CreateWordGroupScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.CryptoManagerModule
import com.xxmrk888ytxx.mydictionary.DI.module.DomainModule
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.mydictionary.DI.module.DatabaseModule
import com.xxmrk888ytxx.mydictionary.DI.module.FeatureViewScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.LanguageIndicatorModule
import com.xxmrk888ytxx.mydictionary.DI.module.ManageLanguageScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.PreferencesStorageModule
import com.xxmrk888ytxx.mydictionary.DI.module.RestoreBackupScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.SettingsScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.TTSManagerModule
import com.xxmrk888ytxx.mydictionary.DI.module.TelegramApiModule
import com.xxmrk888ytxx.mydictionary.DI.module.UseCaseModule
import com.xxmrk888ytxx.mydictionary.DI.module.ViewGroupWordsScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.WordByEarTrainingScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.WordGroupScreenModule
import com.xxmrk888ytxx.mydictionary.DI.module.WordTranslateTrainingScreenModule
import com.xxmrk888ytxx.mydictionary.domain.BillingManager.BillingManager
import com.xxmrk888ytxx.mydictionary.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        CoreModule::class,
        CreateWordGroupScreenModule::class,
        DatabaseModule::class,
        DomainModule::class,
        ViewGroupWordsScreenModule::class,
        EditWordScreenModule::class,
        WordGroupScreenModule::class,
        TTSManagerModule::class,
        WordTranslateTrainingScreenModule::class,
        SettingsScreenModule::class,
        LanguageIndicatorModule::class,
        UseCaseModule::class,
        WordByEarTrainingScreenModule::class,
        CreateBackupScreenModule::class,
        ArchiverCreatorModule::class,
        BackupConverterModule::class,
        RestoreBackupScreenModule::class,
        ManageLanguageScreenModule::class,
        FeatureViewScreenModule::class,
        PreferencesStorageModule::class,
        ApplicationScopeModule::class,
        AdmobModule::class,
        AutoBackupToTelegramScreenModule::class,
        TelegramApiModule::class,
        CryptoManagerModule::class
    ]
)
@AppScope
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    val billingManager:BillingManager

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context:Context) : AppComponent
    }
}