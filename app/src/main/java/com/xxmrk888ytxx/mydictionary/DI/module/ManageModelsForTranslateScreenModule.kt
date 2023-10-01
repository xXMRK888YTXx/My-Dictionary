package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.managetranslatedmodelsscreen.contracts.ProvideTranslateModelsContract
import com.xxmrk888ytxx.mydictionary.glue.ManageModelsForTranslateScreen.ProvideTranslateModelsContractImpl
import dagger.Binds
import dagger.Module

@Module
interface ManageModelsForTranslateScreenModule {

    @Binds
    fun bindProvideTranslateModelsContract(
        provideTranslateModelsContractImpl: ProvideTranslateModelsContractImpl
    ) : ProvideTranslateModelsContract
}