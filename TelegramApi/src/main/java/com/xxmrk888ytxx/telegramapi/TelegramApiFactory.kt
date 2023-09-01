package com.xxmrk888ytxx.telegramapi

abstract class TelegramApiFactory {

    abstract fun create(userId:String,botKey:String) : TelegramApi

    companion object {

        fun ktorFactory() : TelegramApiFactory {
            return KtorTelegramApiFactory()
        }
    }
}