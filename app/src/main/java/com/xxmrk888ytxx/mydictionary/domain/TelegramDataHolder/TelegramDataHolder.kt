package com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder

import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.models.TelegramData
import kotlinx.coroutines.flow.Flow

interface TelegramDataHolder {

    val telegramData: Flow<TelegramData?>

    suspend fun setupData(telegramData: TelegramData)

    suspend fun removeData()
}