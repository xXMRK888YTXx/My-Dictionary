package com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder

import kotlinx.coroutines.flow.Flow

interface FirstStartAppStateHolder {


    val isFirstAppStart: Flow<Boolean>

    suspend fun markAppStart()
}