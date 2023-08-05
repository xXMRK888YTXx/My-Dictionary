package com.xxmrk888ytxx.managelanguagescreen.contract

import com.xxmrk888ytxx.managelanguagescreen.exception.LanguageUsedException
import kotlin.jvm.Throws

interface RemoveLanguageContract {

    @Throws(
        LanguageUsedException::class
    )
    suspend fun removeLanguage(id:Int) : Result<Unit>
}