package com.xxmrk888ytxx.corecompose.theme.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent

@Composable
fun WithUiEventHandler(
    onEvent:(UiEvent) -> Unit,
    content:@Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalUiEventHandler provides onEvent,
        content = content
    )
}