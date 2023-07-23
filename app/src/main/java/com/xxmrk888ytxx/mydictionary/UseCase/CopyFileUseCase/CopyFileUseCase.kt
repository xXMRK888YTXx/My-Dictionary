package com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase

import android.net.Uri

interface CopyFileUseCase {

    suspend fun execute(
        originFile:Uri,
        outputFile:Uri,
        bufferSize:Int = 5000
    ) : Result<Unit>
}