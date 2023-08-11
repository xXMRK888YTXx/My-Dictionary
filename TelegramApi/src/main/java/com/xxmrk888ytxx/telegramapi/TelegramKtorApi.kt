package com.xxmrk888ytxx.telegramapi

import com.xxmrk888ytxx.telegramapi.exception.ApiException
import com.xxmrk888ytxx.telegramapi.exception.NoConnectionException
import com.xxmrk888ytxx.telegramapi.exception.UnknownException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentDisposition
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.ConnectException
import java.net.UnknownHostException

internal class TelegramKtorApi(
    private val httpClient: HttpClient,
    private val userId:String,
    private val botKey:String
) : TelegramApi {

    private val sendMessageUrl:String
        get() = "https://api.telegram.org/bot$botKey/sendMessage"

    private val sendDocumentUrl:String
        get() = "https://api.telegram.org/bot$botKey/sendDocument"

    override suspend fun sendMessage(text: String): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {

            val result = httpClient.get(sendMessageUrl) {
                parameter("chat_id",userId)
                parameter("text",text)
            }

            when(result.status.value) {
                in 200..299 -> Result.success(Unit)
                in 400..499 -> Result.failure(ApiException())
                else -> Result.failure(UnknownException())
            }

        }
        catch (e:ClientRequestException) {
            return@withContext Result.failure(ApiException(e.stackTraceToString()))
        }
        catch (e: IOException) {
            return@withContext Result.failure(NoConnectionException(e.stackTraceToString()))
        }
        catch (e:Exception) {
            Result.failure(UnknownException(e.stackTraceToString()))
        }
    }

    override suspend fun uploadFile(fileBytes: ByteArray,caption:String?): Result<Unit> = withContext(Dispatchers.IO) {
        return@withContext try {

            val formData = formData {
                append(
                    "document",
                    fileBytes,
                    Headers.build {
                        append(
                            HttpHeaders.ContentDisposition,
                            "${ContentDisposition.Parameters.FileName}=\"backup-${System.currentTimeMillis()}\" "
                        )
                    }
                )
            }

            val result = httpClient.post(sendDocumentUrl) {
                parameter("chat_id",userId)

                if(caption != null) {
                    parameter("caption",caption)
                }

                setBody(
                    MultiPartFormDataContent(
                        parts = formData,

                    )
                )

                method = HttpMethod.Post
            }



            when(result.status.value) {
                in 200..299 -> Result.success(Unit)
                in 400..499 -> Result.failure(ApiException())
                else -> Result.failure(UnknownException())
            }

        }
        catch (e:ClientRequestException) {
            return@withContext Result.failure(ApiException(e.stackTraceToString()))
        }
        catch (e: IOException) {
            return@withContext Result.failure(NoConnectionException(e.stackTraceToString()))
        }
        catch (e:Exception) {
            Result.failure(UnknownException(e.stackTraceToString()))
        }
    }
}