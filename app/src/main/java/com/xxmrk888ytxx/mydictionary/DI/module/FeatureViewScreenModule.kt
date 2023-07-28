package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.featureviewscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.featureviewscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.featureviewscreen.contract.ToApplicationContact
import com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen.OpenPrivacyPolicyContractImpl
import com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen.OpenTermsOfUseContractImpl
import com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen.ToApplicationContactImpl
import dagger.Binds
import dagger.Module

@Module
interface FeatureViewScreenModule {

    @Binds
    fun bindOpenTermsOfUseContract(
        openTermsOfUseContractImpl: OpenTermsOfUseContractImpl
    ) : OpenTermsOfUseContract

    @Binds
    fun bindOpenPrivacyPolicyContract(
        openPrivacyPolicyContractImpl: OpenPrivacyPolicyContractImpl
    ) : OpenPrivacyPolicyContract

    @Binds
    fun bindToApplicationContact(
        toApplicationContactImpl: ToApplicationContactImpl
    ) : ToApplicationContact

}