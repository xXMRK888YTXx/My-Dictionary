package com.xxmrk888ytxx.mydictionary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalApplicationName
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.mydictionary.R


/**
 * [Ru]
 * Эта функция оборачивает [NavHost] и предоставляет [Navigator]
 * с помощью CompositionLocalProvider
 */

/**
 * [En]
 * This function wrapping [NavHost] and provided
 * [Navigator] by CompositionLocalProvider
 */
@Composable
fun NavigationHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    navigator: Navigator,
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit
) {
    val applicationName = stringResource(id = R.string.app_name)

    CompositionLocalProvider(
        LocalNavigator provides navigator,
        LocalApplicationName provides applicationName
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = startDestination,
            builder = builder
        )
    }
}