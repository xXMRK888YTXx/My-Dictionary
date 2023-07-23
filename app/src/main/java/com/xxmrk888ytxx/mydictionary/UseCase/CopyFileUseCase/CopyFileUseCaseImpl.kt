package com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import javax.inject.Inject

class CopyFileUseCaseImpl @Inject constructor(
    private val context: Context,
    private val logger:Logger
) : CopyFileUseCase {

    override suspend fun execute(
        originFile: Uri,
        outputFile: Uri,
        bufferSize:Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        var outputStream: BufferedOutputStream? = null
        var inputStream: BufferedInputStream? = null

        return@withContext try {
            val buffer = ByteArray(5000)
            outputStream = BufferedOutputStream(context.contentResolver.openOutputStream(outputFile))
            inputStream = BufferedInputStream(context.contentResolver.openInputStream(originFile))

            var readerBytes = inputStream.read(buffer)

            while (readerBytes != -1) {
                outputStream.write(buffer)
                readerBytes = inputStream.read(buffer)
            }

            logger.info("file copied",LOG_TAG)

            Result.success(Unit)
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)
            Result.failure(e)
        } finally {
            withContext(NonCancellable) {
                outputStream?.close()
                inputStream?.close()

                outputStream = null
                inputStream = null
            }
        }
    }

    companion object {
        const val LOG_TAG = "CopyFileUseCaseImpl"
    }
}