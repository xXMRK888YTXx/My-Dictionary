package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.UseCase.OpenEmailAppForWriteDeveloperUseCase.OpenEmailAppForWriteDeveloperUseCase
import com.xxmrk888ytxx.settingsscreen.contract.OpenEmailClientForWriteDeveloperContract
import javax.inject.Inject

class OpenEmailClientForWriteDeveloperContractImpl @Inject constructor(
    private val openEmailAppForWriteDeveloperUseCase: OpenEmailAppForWriteDeveloperUseCase
) : OpenEmailClientForWriteDeveloperContract {

    override fun open() {
        openEmailAppForWriteDeveloperUseCase.execute()
    }
}