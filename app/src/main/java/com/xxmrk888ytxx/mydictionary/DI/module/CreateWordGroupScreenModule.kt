package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateLanguageContract
import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateWordGroupContract
import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
import com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen.CreateLanguageContractImpl
import com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen.CreateWordGroupContractImpl
import com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen.ProvideLanguagesContractImpl
import com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen.ProvideWordGroupContractImpl
import com.xxmrk888ytxx.wordgroupscreen.contract.ProvideWordGroupContract
import dagger.Binds
import dagger.Module

@Module
interface CreateWordGroupScreenModule {

    @Binds
    fun bindsCreateLanguageContract(createLanguageContract: CreateLanguageContractImpl) : CreateLanguageContract

    @Binds
    fun bindsProvideLanguagesContract(provideLanguagesContractImpl: ProvideLanguagesContractImpl) : ProvideLanguagesContract

    @Binds
    fun bindCreateWorkGroupContract(createWorkGroupContractImpl: CreateWordGroupContractImpl) : CreateWordGroupContract

    @Binds
    fun bindProvideWordGroupContract(ProvideWordGroupContractImpl: ProvideWordGroupContractImpl) : ProvideWordGroupContract
}