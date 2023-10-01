package com.xxmrk888ytxx.mydictionary.glue.ManageModelsForTranslateScreen

import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.ProvideTranslateModelsContract
import com.xxmrk888ytxx.managetranslatedmodelsscreen.models.TranslateModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProvideTranslateModelsContractImpl @Inject constructor(

) : ProvideTranslateModelsContract {

    override val models: Flow<ImmutableList<TranslateModel>> = flowOf(persistentListOf())
}