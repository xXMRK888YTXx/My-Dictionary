package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.TelegramDataHolder
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CheckTelegramDataContractImpl @Inject constructor(
    private val telegramDataHolder: TelegramDataHolder
) : CheckTelegramDataContract {

    override suspend fun isTelegramDataExist(): Boolean {
        return telegramDataHolder.telegramData.first() != null
    }
}