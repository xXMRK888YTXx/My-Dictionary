package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen.ProvideWordForWordGroupContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen.ProvideWordGroupInfoContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen.RemoveWordContractImpl
import com.xxmrk888ytxx.mydictionary.glue.ViewGroupWordsScreen.TextToSpeechContractImpl
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordForWordGroupContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.ProvideWordGroupInfoContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.RemoveWordContract
import com.xxmrk888ytxx.viewgroupwordsscreen.contract.TextToSpeechContract
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

    @Binds
    fun bindTextToSpeechContract(
        TextToSpeechContractImpl: TextToSpeechContractImpl
    ) : TextToSpeechContract

    @Binds
    fun bindRemoveWordContract(
        RemoveWordContractImpl:RemoveWordContractImpl
    ) : RemoveWordContract
}