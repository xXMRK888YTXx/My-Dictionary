package com.xxmrk888ytxx.restorebackupscreen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ActivityContracts.OpenExternalFileContract.OpenExternalFileContract
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.restorebackupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.restorebackupscreen.models.ScreenState


/**
 * [Ru]
 * Экран для восстановления резервной копии
 */

/**
 * [En]
 * Screen for restore from backup
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RestoreBackupScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    BackHandler(
       enabled = screenState.isRestoreInProcess
    ) {}

    val navigator = LocalNavigator.current

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val selectFileContract = rememberLauncherForActivityResult(
        contract = OpenExternalFileContract(),
        onResult = { onEvent(LocalUiEvent.FileSelectedEvent(
            it,
            snackbarHostState,
            scope,
            navigator
        )) }
    )

    Scaffold(
        Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (screenState.isRestoreInProcess) {
                Text(text = stringResource(R.string.please_wait))

                LinearProgressIndicator()
            } else {
                Text(text = stringResource(R.string.select_backup_file_for_restore))

                Button(onClick = {
                    onEvent(
                        LocalUiEvent.SelectFileRequestEvent(selectFileContract)
                    )
                }) {
                    Text(text = stringResource(R.string.select_file))
                }
            }
        }
    }

}