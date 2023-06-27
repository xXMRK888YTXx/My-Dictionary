package com.xxmrk888ytxx.corecompose.theme.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.example.compose.AppTheme
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

@Composable
fun WithLocalProviderForPreview(content:@Composable () -> Unit) {
    val navigator = remember {
        object : Navigator {
            override fun toCreateWordGroupScreen() {

            }

            override fun toViewGroupWordsScreen(wordGroupId: Int) {

            }

            override fun toAddWordScreen(wordGroupId: Int) {

            }

            override fun backScreen() {

            }

        }
    }

    AppTheme(
        useDynamicColors = false
    ) {
        CompositionLocalProvider(
            LocalNavigator provides navigator,
            LocalUiEventHandler provides {},
            content = content
        )
    }
}