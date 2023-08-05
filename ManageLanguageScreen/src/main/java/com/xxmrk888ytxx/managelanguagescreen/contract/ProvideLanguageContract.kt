package com.xxmrk888ytxx.managelanguagescreen.contract

import com.xxmrk888ytxx.managelanguagescreen.models.Language
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface ProvideLanguageContract {

    val languages: Flow<ImmutableList<Language>>
}