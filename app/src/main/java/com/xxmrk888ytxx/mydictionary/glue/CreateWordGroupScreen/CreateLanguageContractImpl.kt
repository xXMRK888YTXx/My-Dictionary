package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateLanguageContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import javax.inject.Inject

class CreateLanguageContractImpl @Inject constructor() : CreateLanguageContract {

    override suspend fun newLanguage(newLanguageName:String) {

    }
}