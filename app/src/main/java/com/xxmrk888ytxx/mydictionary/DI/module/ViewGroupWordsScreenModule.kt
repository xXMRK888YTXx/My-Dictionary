package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen.ProvideWordForWordGroupContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen.ProvideWordGroupInfoContractImpl
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordGroupInfoContract
import dagger.Binds
import dagger.Module

@Module
interface ViewGroupWordsScreenModule {

    @Binds
    fun bindViewGroupWordsScreen(
        provideWordForWordGroupImpl: ProvideWordForWordGroupContractImpl,
    ): ProvideWordForWordGroupContract

    @Binds
    fun bindProvideWordGroupInfoContract(
        provideWordGroupInfoContractImpl: ProvideWordGroupInfoContractImpl,
    ): ProvideWordGroupInfoContract
}