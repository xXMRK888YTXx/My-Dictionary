package com.xxmrk888ytxx.trainingactionsscreen.models

import androidx.annotation.IdRes

internal data class Actions(
    @IdRes val text:Int,
    val onClick:() -> Unit
)
