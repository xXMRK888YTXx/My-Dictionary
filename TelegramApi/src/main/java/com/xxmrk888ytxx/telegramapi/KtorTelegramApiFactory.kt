package com.xxmrk888ytxx.telegramapi

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

internal class KtorTelegramApiFactory() : TelegramApiFactory() {

    private val httpClient: HttpClient by lazy {
        val json = kotlinx.serialization.json.Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }

        HttpClient(Android) {

            engine {
                connectTimeout = 10000
                socketTimeout = 10000
            }

            install(ContentNegotiation) {
                json(
                    json = json
                )
            }

            install(Logging)

            addDefaultResponseValidation()
        }
    }

    override fun create(userId: String, botKey: String): TelegramApi {
        return TelegramKtorApi(httpClient, userId, botKey)
    }
}