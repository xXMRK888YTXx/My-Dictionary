package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.UseCase.OpenTermsOfUseUseCase.OpenTermsOfUseUseCase
import com.xxmrk888ytxx.settingsscreen.contract.OpenTermsOfUseContract
import javax.inject.Inject

class OpenTermsOfUseContractImpl @Inject constructor(
    private val openTermsOfUseUseCase: OpenTermsOfUseUseCase
) : OpenTermsOfUseContract {

    override fun open() {
        openTermsOfUseUseCase.execute()
    }
}