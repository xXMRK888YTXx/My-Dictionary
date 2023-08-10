package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.SaveTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.TelegramDataHolder
import javax.inject.Inject
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.models.TelegramData as DomainTelegramData


/**
 * com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.models.TelegramData = DomainTelegramData
 */

class SaveTelegramDataContractImpl @Inject constructor(
    private val telegramDataHolder: TelegramDataHolder
) : SaveTelegramDataContract {
    override suspend fun saveTelegramData(telegramData: TelegramData) {
        telegramDataHolder.setupData(telegramData.toDomainModel())
    }

    private fun TelegramData.toDomainModel() : DomainTelegramData {
        return DomainTelegramData(userId, botKey)
    }
}