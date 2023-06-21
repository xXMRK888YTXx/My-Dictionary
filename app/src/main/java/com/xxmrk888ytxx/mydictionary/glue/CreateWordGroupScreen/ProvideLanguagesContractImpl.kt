package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.createwordgroupscreen.contract.ProvideLanguagesContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//Temp Impl
class ProvideLanguagesContractImpl @Inject constructor(

) : ProvideLanguagesContract {


    override val languages: Flow<ImmutableList<Language>> = MutableStateFlow(
        persistentListOf(
            Language(0,"Русский"),
            Language(1,"English"),
            Language(2,"Беларуский"),
        )
    )
}