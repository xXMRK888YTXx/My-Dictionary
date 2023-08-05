package com.xxmrk888ytxx.mydictionary.glue.SettingsScreen

import com.xxmrk888ytxx.mydictionary.UseCase.OpenSourceCodeUseCase.OpenSourceCodeUseCase
import com.xxmrk888ytxx.settingsscreen.contract.OpenSourceCodeContract
import javax.inject.Inject

class OpenSourceCodeContractImpl @Inject constructor(
    private val openSourceCodeUseCase: OpenSourceCodeUseCase
) : OpenSourceCodeContract {

    override fun open() {
        openSourceCodeUseCase.execute()
    }
}