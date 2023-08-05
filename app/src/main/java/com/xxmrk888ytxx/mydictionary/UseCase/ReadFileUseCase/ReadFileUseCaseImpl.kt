package com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase

import android.content.Context
import android.net.Uri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.io.FileInputStream
import java.io.InputStream
import javax.inject.Inject

class ReadFileUseCaseImpl @Inject constructor(
    private val context: Context,
    private val logger: Logger
) : ReadFileUseCase {

    override suspend fun execute(fileUri: Uri): Result<ByteArray> = withContext(Dispatchers.IO) {
        var inputStream:InputStream? = null

        return@withContext try {
            inputStream = context.contentResolver.openInputStream(fileUri)

            Result.success(inputStream!!.readBytes())
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)
            Result.failure(e)
        } finally {
            withContext(NonCancellable) {
                inputStream?.close()
                inputStream = null
            }
        }
    }

    companion object {
        private const val LOG_TAG = "ReadFileUseCaseImpl"
    }
}