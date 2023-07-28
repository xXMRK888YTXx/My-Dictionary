package com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen

import com.xxmrk888ytxx.featureviewscreen.contract.OpenPrivacyPolicyContract
import com.xxmrk888ytxx.mydictionary.UseCase.OpenPrivacyPolicyUseCase.OpenPrivacyPolicyUseCase
import javax.inject.Inject

class OpenPrivacyPolicyContractImpl @Inject constructor(
    private val openPrivacyPolicyUseCase: OpenPrivacyPolicyUseCase
) : OpenPrivacyPolicyContract {

    override fun openPrivacyPolicy() {
        openPrivacyPolicyUseCase.execute()
    }
}