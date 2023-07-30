package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.BuyRemoveAdsContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.OpenEmailClientForWriteDeveloperContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.OpenPrivacyPolicyContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.OpenSourceCodeContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.OpenTermsOfUseContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.ProvideApplicationVersionImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.RestorePurchasesContractImpl
import com.xxmrk888ytxx.settingsscreen.contract.BuyRemoveAdsContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenEmailClientForWriteDeveloperContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenSourceCodeContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersion
import com.xxmrk888ytxx.settingsscreen.contract.RestorePurchasesContract
import dagger.Binds
import dagger.Module

@Module
interface SettingsScreenModule {

    @Binds
    fun bindProvideApplicationVersion(
        provideApplicationVersionImpl: ProvideApplicationVersionImpl
    ): ProvideApplicationVersion

    @Binds
    fun bindOpenPrivacyPolicyContract(
        openPrivacyPolicyContractImpl: OpenPrivacyPolicyContractImpl
    ) : OpenPrivacyPolicyContract

    @Binds
    fun bindOpenTermsOfUseContract(
        openTermsOfUseContractImpl: OpenTermsOfUseContractImpl
    ) : OpenTermsOfUseContract

    @Binds
    fun bindOpenSourceCodeContract(
        openSourceCodeContractImpl: OpenSourceCodeContractImpl
    ) : OpenSourceCodeContract

    @Binds
    fun bindOpenEmailClientForWriteDeveloperContract(
        openEmailClientForWriteDeveloperContractImpl: OpenEmailClientForWriteDeveloperContractImpl
    ) : OpenEmailClientForWriteDeveloperContract

    @Binds
    fun bindBuyRemoveAdsContract(
        buyRemoveAdsContractImpl: BuyRemoveAdsContractImpl
    ) : BuyRemoveAdsContract

    @Binds
    fun bindRestorePurchasesContract(
        restorePurchasesContractImpl: RestorePurchasesContractImpl
    ) : RestorePurchasesContract
}