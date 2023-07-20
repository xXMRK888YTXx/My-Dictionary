package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.glue.WordByEarTrainingScreen.GenerateQuestionForTrainingContractImpl
import com.xxmrk888ytxx.mydictionary.glue.WordByEarTrainingScreen.PlayWordContractImpl
import com.xxmrk888ytxx.mydictionary.glue.WordByEarTrainingScreen.ProvideWordGroupsContractImpl
import com.xxmrk888ytxx.wordbyeartrainingscreen.contract.GenerateQuestionForTrainingContract
import com.xxmrk888ytxx.wordbyeartrainingscreen.contract.PlayWordContract
import com.xxmrk888ytxx.wordbyeartrainingscreen.contract.ProvideWordGroupsContract
import dagger.Binds
import dagger.Module

@Module
interface WordByEarTrainingScreenModule {

    @Binds
    fun bindsProvideWordGroupsContract(
        ProvideWordGroupsContractImpl: ProvideWordGroupsContractImpl
    ) : ProvideWordGroupsContract

    @Binds
    fun bindsGenerateQuestionForTrainingContract(
        GenerateQuestionForTrainingContractImpl: GenerateQuestionForTrainingContractImpl
    ) : GenerateQuestionForTrainingContract

    @Binds
    fun bindPlayWordContract(
        PlayWordContractImpl: PlayWordContractImpl
    ) : PlayWordContract
}