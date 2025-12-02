package com.xxmrk888ytxx.mydictionary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalSnackbarHostState


/**
 * [Ru]
 * Эта функция обертывает [Scaffold] и предоставляет
 * [SnackbarHost], используя CompositionLocalProvider
 */

/**
 * [En]
 * This function wraps [Scaffold] and
 * provides [SnackbarHost] using CompositionLocalProvider
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SnackBarScaffold(content:@Composable (PaddingValues) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(
        LocalSnackbarHostState provides snackbarHostState
    ) {
        Scaffold(
            Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = WindowInsets()
        ) {
            content(it)
        }
    }
}