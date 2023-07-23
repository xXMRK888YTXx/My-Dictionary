package com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase

import android.net.Uri
import java.io.File

interface FileWriterUseCase {

    suspend fun toFile(file:File, data:ByteArray)

    suspend fun byUri(uri: Uri,data:ByteArray)
}