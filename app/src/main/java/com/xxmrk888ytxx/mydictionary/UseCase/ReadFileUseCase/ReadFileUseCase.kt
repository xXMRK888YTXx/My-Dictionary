package com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase

import android.net.Uri

interface ReadFileUseCase {

    suspend fun execute(fileUri:Uri) : Result<ByteArray>
}