package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.mydictionary.glue.WordTranslateTrainingScreen.ProvideWordGroupsContractImpl
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.ProvideWordGroupsContract
import dagger.Binds
import dagger.Module

@Module
interface WordTranslateTrainingScreenModule {

    @Binds
    @AppScope
    fun bindProvideWordGroupsContract(ProvideWordGroupsContract: ProvideWordGroupsContractImpl) : ProvideWordGroupsContract
}