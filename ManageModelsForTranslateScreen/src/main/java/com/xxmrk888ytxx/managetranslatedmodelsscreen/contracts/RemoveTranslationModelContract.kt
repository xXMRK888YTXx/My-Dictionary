package com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts

import kotlinx.coroutines.flow.Flow

interface RemoveTranslationModelContract {

    fun removeModel(code:String) : Flow<Result<Unit>>
}