package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.core.net.toUri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCase
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
    private val context: Context,
    private val copyFileUseCase: CopyFileUseCase
) : ImageRepository {


    override suspend fun addImage(imagePath: String): String? = withContext(Dispatchers.IO) {
        logger.info("addImage", LOG_TAG)

        val outputFile = File(imageDir,System.currentTimeMillis().toString())

        val result = copyFileUseCase.execute(
            imagePath.toUri(),
            outputFile.toUri()
        )

        return@withContext if(result.isSuccess) outputFile.absolutePath else null
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