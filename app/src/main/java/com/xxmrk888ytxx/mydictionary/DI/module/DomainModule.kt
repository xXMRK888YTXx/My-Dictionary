package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.DI.Qualifiers.BillingScopeQualifier
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.mydictionary.domain.AdsStateManager.AdsStateManager
import com.xxmrk888ytxx.mydictionary.domain.AdsStateManager.AdsStateManagerImpl
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.AutoBackupTelegramSettingsHolder
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupTelegramSettingsHolder.AutoBackupTelegramSettingsHolderImpl
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupToTelegramLastBackupHashHolder.AutoBackupToTelegramLastBackupHashHolder
import com.xxmrk888ytxx.mydictionary.domain.AutoBackupToTelegramLastBackupHashHolder.AutoBackupToTelegramLastBackupHashHolderImpl
import com.xxmrk888ytxx.mydictionary.domain.BillingManager.BillingManager
import com.xxmrk888ytxx.mydictionary.domain.BillingManager.BillingManagerImpl
import com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder.FirstStartAppStateHolder
import com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder.FirstStartAppStateHolderImpl
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepositoryImpl
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepositoryImpl
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepositoryImpl
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepositoryImpl
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepositoryImpl
import com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider.RestoreBackupStrategyProvider
import com.xxmrk888ytxx.mydictionary.domain.RestoreBackupStrategyProvider.RestoreBackupStrategyProviderImpl
import com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider.SupportedLanguagesForTranslateProvider
import com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider.SupportedLanguagesForTranslateProviderImpl
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.TelegramDataHolder
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.TelegramDataHolderImpl
import com.xxmrk888ytxx.mydictionary.domain.VersionProvider.VersionProvider
import com.xxmrk888ytxx.mydictionary.domain.VersionProvider.VersionProviderImpl
import com.xxmrk888ytxx.mydictionary.glue.TranslatorScreen.ProvideSupportedLanguagesImpl
import com.xxmrk888ytxx.translatorscreen.contract.ProvideSupportedLanguages
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob

@Module
interface DomainModule {

    @Binds
    fun bindLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl) : LanguageRepository

    @Binds
    fun bindWordGroupRepository(wordGroupRepository: WordGroupRepositoryImpl) : WordGroupRepository

    @Binds
    fun bindImageRepository(imageRepository: ImageRepositoryImpl) : ImageRepository

    @Binds
    fun bindWordRepository(wordRepository: WordRepositoryImpl) : WordRepository

    @Binds
    fun bindWordPhrasesRepositoryImpl(wordPhrasesRepositoryImpl: WordPhrasesRepositoryImpl) : WordPhrasesRepository

    @Binds
    fun bindVersionProvider(versionProviderImpl: VersionProviderImpl) : VersionProvider

    @Binds
    fun bindRestoreBackupStrategyProvider(
        restoreBackupStrategyProviderImpl: RestoreBackupStrategyProviderImpl
    ) : RestoreBackupStrategyProvider

    @Binds
    fun bindFirstStartAppStateHolder(
        firstStartAppStateHolderImpl: FirstStartAppStateHolderImpl
    ) : FirstStartAppStateHolder

    @Binds
    @AppScope
    fun bindBillingManager(
        billingManagerImpl: BillingManagerImpl
    ) : BillingManager

    @Binds
    fun bindAdsStateManager(
        adsStateManagerImpl: AdsStateManagerImpl
    ) : AdsStateManager

    @Binds
    fun bindTelegramDataHolder(
        telegramDataHolderImpl: TelegramDataHolderImpl
    ) : TelegramDataHolder

    @Binds
    fun bindAutoBackupTelegramSettingsHolder(
        autoBackupTelegramSettingsHolderImpl: AutoBackupTelegramSettingsHolderImpl
    ) : AutoBackupTelegramSettingsHolder

    @Binds
    fun bindAutoBackupToTelegramLastBackupHashHolder(
        autoBackupToTelegramLastBackupHashHolderImpl: AutoBackupToTelegramLastBackupHashHolderImpl
    ) : AutoBackupToTelegramLastBackupHashHolder

    @Binds
    fun bindSupportedLanguagesForTranslateProvider(
        supportedLanguagesForTranslateProviderImpl: SupportedLanguagesForTranslateProviderImpl
    ) : SupportedLanguagesForTranslateProvider

    @Binds
    fun bindProvideSupportedLanguages(
        provideSupportedLanguagesImpl: ProvideSupportedLanguagesImpl
    ) : ProvideSupportedLanguages


    companion object {

        @OptIn(ExperimentalCoroutinesApi::class)
        @Provides
        @AppScope
        @BillingScopeQualifier
        fun provideBullingScope() : CoroutineScope {
            return CoroutineScope(Dispatchers.IO.limitedParallelism(1) + SupervisorJob())
        }
    }
}