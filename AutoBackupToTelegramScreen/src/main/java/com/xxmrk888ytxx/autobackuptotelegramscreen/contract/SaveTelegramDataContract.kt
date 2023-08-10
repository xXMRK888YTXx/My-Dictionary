package com.xxmrk888ytxx.autobackuptotelegramscreen.contract

import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData

interface SaveTelegramDataContract {

    suspend fun saveTelegramData(telegramData: TelegramData)
}