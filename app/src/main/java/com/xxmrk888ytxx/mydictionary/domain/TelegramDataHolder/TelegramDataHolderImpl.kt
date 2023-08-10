package com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder

import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxmrk888ytxx.mydictionary.domain.TelegramDataHolder.models.TelegramData
import com.xxmrk888ytxx.preferencesstorage.EncryptedPreferencesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.nio.charset.StandardCharsets.UTF_8
import javax.inject.Inject

class TelegramDataHolderImpl @Inject constructor(
    private val encryptedPreferencesStorage: EncryptedPreferencesStorage
) : TelegramDataHolder {

    private val userIdKey = stringPreferencesKey("userIdKey")

    private val botKeyKey = stringPreferencesKey("botKeyKey")

    private val userIdFlow = encryptedPreferencesStorage.getPropertyOrNull(userIdKey).map { data ->
        if(data == null) return@map null

        data.toString(UTF_8)
    }

    private val boyKeyFlow = encryptedPreferencesStorage.getPropertyOrNull(botKeyKey).map { data ->
        if(data == null) return@map null

        data.toString(UTF_8)
    }

    override val telegramData: Flow<TelegramData?> = combine(
        userIdFlow,
        boyKeyFlow
    ) { userId,botKey ->
        if(userId == null || botKey == null) return@combine null

        return@combine TelegramData(userId, botKey)
    }

    override suspend fun setupData(telegramData: TelegramData) {
        encryptedPreferencesStorage.writeProperty(userIdKey,telegramData.userId.toByteArray())
        encryptedPreferencesStorage.writeProperty(botKeyKey,telegramData.botKey.toByteArray())
    }

    override suspend fun removeData() {
        encryptedPreferencesStorage.removeProperty(userIdKey)
        encryptedPreferencesStorage.removeProperty(botKeyKey)
    }
}