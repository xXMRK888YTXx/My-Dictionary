package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData
import javax.inject.Inject

class CheckTelegramDataContractImpl @Inject constructor(

) : CheckTelegramDataContract {

    override suspend fun checkData(telegramData: TelegramData): Boolean {
        return false
    }
}