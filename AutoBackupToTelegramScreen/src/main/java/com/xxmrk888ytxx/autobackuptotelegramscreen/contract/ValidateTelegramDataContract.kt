package com.xxmrk888ytxx.autobackuptotelegramscreen.contract

import com.xxmrk888ytxx.autobackuptotelegramscreen.exception.NoInternetException
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData
import kotlin.jvm.Throws

interface ValidateTelegramDataContract {

    @Throws(NoInternetException::class)
    suspend fun validateData(telegramData: TelegramData) : Boolean
}