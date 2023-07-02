package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.addwordscreen.contracts.ProvideWordInfoContract
import com.xxmrk888ytxx.addwordscreen.contracts.ProvideWordPhrasesContract
import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordContract
import com.xxmrk888ytxx.addwordscreen.contracts.SaveWordPhraseContract
import com.xxmrk888ytxx.mydictionary.glue.EditWordScreen.ProvideWordInfoContractImpl
import com.xxmrk888ytxx.mydictionary.glue.EditWordScreen.ProvideWordPhrasesContractImpl
import com.xxmrk888ytxx.mydictionary.glue.EditWordScreen.SaveWordContractImpl
import com.xxmrk888ytxx.mydictionary.glue.EditWordScreen.SaveWordPhraseContractImpl
import dagger.Binds
import dagger.Module

@Module
interface EditWordScreenModule {

    @Binds
    fun bindSaveWordContract(saveWordContractImpl: SaveWordContractImpl): SaveWordContract

    @Binds
    fun bindSaveWordPhraseContract(saveWordPhraseContractImpl: SaveWordPhraseContractImpl): SaveWordPhraseContract

    @Binds
    fun bindProvideWordPhrasesContract(
        provideWordPhrasesContractImpl: ProvideWordPhrasesContractImpl
    ) : ProvideWordPhrasesContract

    @Binds
    fun bindProvideWordInfoContract(
        ProvideWordInfoContractImpl: ProvideWordInfoContractImpl
    ) : ProvideWordInfoContract
}