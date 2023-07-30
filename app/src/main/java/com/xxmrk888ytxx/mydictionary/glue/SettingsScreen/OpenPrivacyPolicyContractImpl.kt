package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.UseCase.OpenPrivacyPolicyUseCase.OpenPrivacyPolicyUseCase
import com.xxmrk888ytxx.settingsscreen.contract.OpenPrivacyPolicyContract
import javax.inject.Inject

class OpenPrivacyPolicyContractImpl @Inject constructor(
    private val openPrivacyPolicyUseCase: OpenPrivacyPolicyUseCase
) : OpenPrivacyPolicyContract {

    override fun open() {
        openPrivacyPolicyUseCase.execute()
    }
}