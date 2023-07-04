package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
import com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen.ProvideLanguagesContractImpl
import com.xxmrk888ytxx.mydictionary.glue.WordGroupScreen.RemoveWordGroupContractImpl
import com.xxmrk888ytxx.wordgroupscreen.contract.RemoveWordGroupContract
import dagger.Binds
import dagger.Module

@Module
interface WordGroupScreenModule {

    @Binds
    fun bindsProvideLanguagesContract(
        provideLanguagesContractImpl: ProvideLanguagesContractImpl
    ) : ProvideLanguagesContract

    @Binds
    fun bindsRemoveWordGroupContractImpl(
        removeWordGroupContractImpl: RemoveWordGroupContractImpl
    ) : RemoveWordGroupContract
}