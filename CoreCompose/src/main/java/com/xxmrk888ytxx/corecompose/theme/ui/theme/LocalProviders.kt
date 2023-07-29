package com.xxmrk888ytxx.corecompose.theme.ui.theme

import AdController
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator


val LocalNavigator = compositionLocalOf<Navigator> {
    error("Navigator is not provided")
}

val LocalUiEventHandler = compositionLocalOf<(UiEvent) -> Unit> {
    error("Ui event handler not provided")
}

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("Snackbar host not provided")
}

val LocalApplicationName = staticCompositionLocalOf<String> {
    error("LocalApplicationName not provided")
}

val LocalAdController = compositionLocalOf<AdController> {
    error("LocalAdController not provided")
}