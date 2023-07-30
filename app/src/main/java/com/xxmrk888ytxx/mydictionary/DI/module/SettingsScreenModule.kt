package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.OpenPrivacyPolicyContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.OpenTermsOfUseContractImpl
import com.xxmrk888ytxx.mydictionary.glue.SettingsScreen.ProvideApplicationVersionImpl
import com.xxmrk888ytxx.settingsscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.settingsscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.settingsscreen.contract.ProvideApplicationVersion
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
}