package com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI

import kotlinx.coroutines.flow.Flow

interface UiStateHolder<out STATE> {

    val state:Flow<STATE>

    val defValue:STATE
}