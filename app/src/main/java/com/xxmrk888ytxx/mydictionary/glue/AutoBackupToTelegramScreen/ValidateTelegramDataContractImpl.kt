package com.xxmrk888ytxx.mydictionary.glue.AutoBackupToTelegramScreen

import android.content.Context
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ValidateTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.exception.NoInternetException
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.R
import com.xxmrk888ytxx.telegramapi.TelegramApiFactory
import com.xxmrk888ytxx.telegramapi.exception.ApiException
import com.xxmrk888ytxx.telegramapi.exception.NoConnectionException
import javax.inject.Inject

class ValidateTelegramDataContractImpl @Inject constructor(
    private val telegramApiFactory: TelegramApiFactory,
    private val logger: Logger,
    private val context: Context
) : ValidateTelegramDataContract {

    override suspend fun validateData(telegramData: TelegramData): Boolean {
        val api = telegramApiFactory.create(telegramData.userId,telegramData.botKey)

        api.sendMessage(context.getString(R.string.this_is_test_message))
            .onSuccess { return true }
            .onFailure {
                logger.error(it, LOG_TAG)
                when(it) {
                    is ApiException -> {
                        return false
                    }

                    is NoConnectionException -> {
                        throw NoInternetException()
                    }

                    else -> throw it
                }
            }

        return false
    }

    companion object {
        private const val LOG_TAG = "CheckTelegramDataContractImpl"
    }
}