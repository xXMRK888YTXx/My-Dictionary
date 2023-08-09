package com.xxmrk888ytxx.autobackuptotelegramscreen.contract

import com.xxmrk888ytxx.autobackuptotelegramscreen.exception.NoInternetException
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData
import kotlin.jvm.Throws

interface CheckTelegramDataContract {

    @Throws(NoInternetException::class)
    suspend fun checkData(telegramData: TelegramData) : Boolean
}