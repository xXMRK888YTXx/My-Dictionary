package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.RemoveTelegramDataContract
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.TelegramDataHolder
import javax.inject.Inject

class RemoveTelegramDataContractImpl @Inject constructor(
    private val telegramDataHolder: TelegramDataHolder
) : RemoveTelegramDataContract {

    override suspend fun remove() {
        telegramDataHolder.removeData()
    }
}