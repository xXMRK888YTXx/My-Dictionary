package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.ProvideTranslateModelsContract
import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.RemoveTranslationModelContract
import com.xxmrk888ytxx.mydictionary.glue.ManageModelsForTranslateScreen.ProvideTranslateModelsContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ManageModelsForTranslateScreen.RemoveTranslationModelContractImpl
import dagger.Binds
import dagger.Module

@Module
interface ManageModelsForTranslateScreenModule {

    @Binds
    fun bindProvideTranslateModelsContract(
        provideTranslateModelsContractImpl: ProvideTranslateModelsContractImpl
    ) : ProvideTranslateModelsContract

    @Binds
    fun bindRemoveTranslationModelContract(
        removeTranslationModelContractImpl: RemoveTranslationModelContractImpl
    ) : RemoveTranslationModelContract
}