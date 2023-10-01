package com.xxmrk888ytxx.mydictionary.glue.ManageModelsForTranslateScreen

import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.RemoveTranslationModelContract
import com.xxmrk888ytxx.translator.Translator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveTranslationModelContractImpl @Inject constructor(
    private val translator: Translator
) : RemoveTranslationModelContract {

    override fun removeModel(code: String): Flow<Result<Unit>> {
        return translator.removeModel(code)
    }
}