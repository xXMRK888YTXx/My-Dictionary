package com.xxmrk888ytxx.corecompose.theme.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator


val LocalNavigator = compositionLocalOf<Navigator> {
    error("Navigator is not provided")
}

val LocalUiEventHandler = compositionLocalOf<(UiEvent) -> Unit> {
    error("Ui event handler not provided")
}