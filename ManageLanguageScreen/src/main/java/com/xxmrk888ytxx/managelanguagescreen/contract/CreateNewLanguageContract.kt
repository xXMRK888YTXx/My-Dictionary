package com.xxmrk888ytxx.managelanguagescreen.contract

interface CreateNewLanguageContract {

    suspend fun createNewLanguage(newLanguageName:String)
}