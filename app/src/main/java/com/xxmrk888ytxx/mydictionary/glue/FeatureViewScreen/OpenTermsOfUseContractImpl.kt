package com.xxmrk888ytxx.mydictionary.glue.FeatureViewScreen

import com.xxmrk888ytxx.featureviewscreen.contract.OpenTermsOfUseContract
import com.xxmrk888ytxx.mydictionary.UseCase.OpenTermsOfUseUseCase.OpenTermsOfUseUseCase
import javax.inject.Inject

class OpenTermsOfUseContractImpl @Inject constructor(
    private val termsOfUseUseCase: OpenTermsOfUseUseCase
) : OpenTermsOfUseContract {

    override fun openTermsOfUse() {
        termsOfUseUseCase.execute()
    }
}