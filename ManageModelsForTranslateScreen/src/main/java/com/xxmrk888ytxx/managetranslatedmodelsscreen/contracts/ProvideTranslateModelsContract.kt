package com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts

import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.TranslateModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideTranslateModelsContract {

    val models: Flow<ImmutableList<TranslateModel>>
}