package com.xxmrk888ytxx.corecompose.theme.ui.theme.models

import androidx.annotation.IdRes

data class BottomSheetDialogItem(
    val text:String,
    @IdRes val icon:Int,
    val onClick:() -> Unit
)
