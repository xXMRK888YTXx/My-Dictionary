package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordContract
import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordPhraseContract
import com.xxmrk888ytxx.mydictionary.glue.AddWordScreen.SaveWordContractImpl
import com.xxmrk888ytxx.mydictionary.glue.AddWordScreen.SaveWordPhraseContractImpl
import dagger.Binds
import dagger.Module

@Module
interface AddWordScreenModule {

    @Binds
    fun bindSaveWordContract(saveWordContractImpl: SaveWordContractImpl): SaveWordContract

    @Binds
    fun bindSaveWordPhraseContract(saveWordPhraseContractImpl: SaveWordPhraseContractImpl): SaveWordPhraseContract
}