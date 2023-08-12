package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import android.content.Context
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.OpenWhereToGetInstructionsContract
import com.xxmrk888ytxx.mydictionary.R
import com.xxmrk888ytxx.mydictionary.UseCase.OpenWhereGetTelegramDataSiteUseCase.OpenWhereGetTelegramDataSiteUseCase
import javax.inject.Inject

class OpenWhereToGetInstructionsContractImpl @Inject constructor(
    private val openWhereGetTelegramDataSiteUseCase: OpenWhereGetTelegramDataSiteUseCase
) : OpenWhereToGetInstructionsContract {

    override fun openInstructions() {
        openWhereGetTelegramDataSiteUseCase.execute()
    }
}