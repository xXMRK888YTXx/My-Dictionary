package com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileWriterUseCaseImpl @Inject constructor() : FileWriterUseCase {
    override suspend fun toFile(file: File, data: ByteArray) = withContext(Dispatchers.IO) {
        var fileOutputStream:FileOutputStream? = null

        try {
            fileOutputStream = FileOutputStream(file)

            fileOutputStream.use {
                it.write(data)
            }
        } finally {
            withContext(NonCancellable) {
                fileOutputStream?.close()
            }
        }
    }

    override suspend fun byUri(uri: Uri, data: ByteArray) {

    }
}