package com.xxmrk888ytxx.corecompose.theme.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.example.compose.AppTheme
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator

@Composable
fun WithLocalProviderForPreview(content: @Composable () -> Unit) {
    val navigator = remember {
        object : Navigator {
            override fun toCreateWordGroupScreen() {

            }

            override fun toViewGroupWordsScreen(wordGroupId: Int) {

            }

            override fun toEditWordScreen(wordGroupId: Int, editWordId: Int) {

            }

            override fun backScreen() {

            }

            override fun toWordTranslateTraining() {

            }

            override fun toWordByEarTraining() {

            }

            override fun toCreateBackupScreen() {

            }

            override fun toRestoreBackupScreen() {

            }

            override fun toLanguageManageScreen() {

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