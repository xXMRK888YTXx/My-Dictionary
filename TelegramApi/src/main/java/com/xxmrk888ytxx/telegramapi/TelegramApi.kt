package com.xxmrk888ytxx.telegramapi

import com.xxmrk888ytxx.telegramapi.exception.ApiException
import com.xxmrk888ytxx.telegramapi.exception.NoConnectionException
import com.xxmrk888ytxx.telegramapi.exception.UnknownException
import kotlin.jvm.Throws

interface TelegramApi {

    @Throws(NoConnectionException::class,ApiException::class,UnknownException::class)
    suspend fun sendMessage(text:String) : Result<Unit>
}