package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.managelanguagescreen.contract.CreateNewLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.contract.ProvideLanguageContract
import com.xxmrk888ytxx.managelanguagescreen.contract.RemoveLanguageContract
import com.xxmrk888ytxx.mydictionary.glue.ManageLanguageScreen.CreateNewLanguageContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ManageLanguageScreen.ProvideLanguageContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ManageLanguageScreen.RemoveLanguageContractImpl
import dagger.Binds
import dagger.Module

@Module
interface ManageLanguageScreenModule {

    @Binds
    fun bindProvideLanguageContract(
        provideLanguageContractImpl: ProvideLanguageContractImpl
    ) : ProvideLanguageContract

    @Binds
    fun bindRemoveLanguageContract(
        removeLanguageContractImpl: RemoveLanguageContractImpl
    ) : RemoveLanguageContract

    @Binds
    fun bindCreateNewLanguageContract(
        createNewLanguageContractImpl: CreateNewLanguageContractImpl
    ) : CreateNewLanguageContract
}