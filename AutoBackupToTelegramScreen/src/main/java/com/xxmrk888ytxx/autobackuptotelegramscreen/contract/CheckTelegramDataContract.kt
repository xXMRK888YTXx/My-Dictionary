package com.xxmrk888ytxx.autobackuptotelegramscreen.contract

interface CheckTelegramDataContract {

    suspend fun isTelegramDataExist() : Boolean
}