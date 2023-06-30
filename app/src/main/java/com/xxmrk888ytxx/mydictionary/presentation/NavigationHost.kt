package com.xxmrk888ytxx.mydictionary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator

@Composable
fun NavigationHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
    navigator: Navigator,
    startDestination: String,
    builder: NavGraphBuilder.() -> Unit
) {
    CompositionLocalProvider(
        LocalNavigator provides navigator
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