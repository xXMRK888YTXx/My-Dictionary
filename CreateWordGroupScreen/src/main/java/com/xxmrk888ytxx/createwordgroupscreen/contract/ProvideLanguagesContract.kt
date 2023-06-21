package com.xxmrk888ytxx.createwordgroupscreen.contract

import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideLanguagesContract {

    val languages: Flow<ImmutableList<Language>>
}