package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.core.net.toUri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val logger: Logger,
    private val context: Context
) : ImageRepository {


    override suspend fun addImage(imagePath: String): String? = withContext(Dispatchers.IO) {
        logger.info("addImage", LOG_TAG)

        val outputFile = File(imageDir,System.currentTimeMillis().toString())

        var outputStream: BufferedOutputStream? = null
        var inputStream: BufferedInputStream? = null

        return@withContext try {
            val buffer = ByteArray(5000)
            outputStream = BufferedOutputStream(FileOutputStream(outputFile))
            inputStream = BufferedInputStream(context.contentResolver.openInputStream(imagePath.toUri()))

            var readerBytes = inputStream.read(buffer)

            while (readerBytes != -1) {
                outputStream.write(buffer)
                readerBytes = inputStream.read(buffer)
            }

            logger.info("export image successful", LOG_TAG)

            outputFile.absolutePath
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)
            null
        } finally {
            withContext(NonCancellable) {
                outputStream?.close()
                inputStream?.close()

                outputStream = null
                inputStream = null
            }
        }
    }

    override suspend fun removeImage(imagePath: String) : Unit = withContext(Dispatchers.IO) {
        try {
            logger.info("Remove image result: ${File(imagePath).delete()}", LOG_TAG)
        }catch (e:Exception) {
            logger.error("In remove file process, has been throw exception", LOG_TAG)
            logger.error(e, LOG_TAG)
        }
    }

    private val imageDir : File
        get() = ContextWrapper(context).getDir("WordGroupImages", Context.MODE_PRIVATE)

    companion object {
        private const val LOG_TAG = "ImageRepositoryImpl"
    }
}