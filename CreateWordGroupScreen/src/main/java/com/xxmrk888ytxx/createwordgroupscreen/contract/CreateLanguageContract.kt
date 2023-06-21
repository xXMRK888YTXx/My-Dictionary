package com.xxmrk888ytxx.createwordgroupscreen.contract

import com.xxmrk888ytxx.createwordgroupscreen.models.Language

interface CreateLanguageContract {

    suspend fun newLanguage(newLanguageName:String)
}