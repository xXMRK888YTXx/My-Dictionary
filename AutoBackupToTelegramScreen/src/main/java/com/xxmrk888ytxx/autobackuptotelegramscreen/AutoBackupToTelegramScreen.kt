package com.xxmrk888ytxx.autobackuptotelegramscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.LocalUiEvent
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.ScreenState
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.ScreenType
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator

@Composable
fun AutoBackupToTelegramScreen(
    screenState: ScreenState,
    onEvent:(UiEvent) -> Unit
) {
    val navigator = LocalNavigator.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            TopBar(
                screenType = screenState.screenType,
                onBack = { onEvent(LocalUiEvent.OnBackEvent(navigator)) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            when(screenState.screenType) {
                ScreenType.INPUT_TELEGRAM_DATA -> {
                    InputTelegramDataScreenType(
                        userIdText = screenState.userIdText,
                        botKeyText = screenState.botKeyText,
                        onUserIdTextChanged = { onEvent(LocalUiEvent.UserIdTextChangedEvent(it)) },
                        onBotKeyTextChanged = { onEvent(LocalUiEvent.BotKeyTextChangedEvent(it)) },
                        onSaveTelegramData = { onEvent(LocalUiEvent.SaveTelegramDataEvent(snackbarHostState)) },
                        onWhereToGet = { onEvent(LocalUiEvent.WhereToGetEvent) },
                        isSaveTelegramDataButtonAvailable = screenState.isSaveTelegramDataAvailable,
                    )
                }

                ScreenType.BACKUP_SETTINGS -> BackupSettingsScreenType()

                ScreenType.LOADING -> LoadingScreenType()
            }
        }
    }
}

@Composable
fun BackupSettingsScreenType() {
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    screenType: ScreenType,
    onBack:() -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when(screenType) {
                    ScreenType.INPUT_TELEGRAM_DATA -> "Enter telegram data"
                    ScreenType.BACKUP_SETTINGS -> ""
                    ScreenType.LOADING -> "Loading"
                }
            )
        },
        navigationIcon = {
            BackNavigationButton(
                onClick = onBack
            )
        }
    )
}

@Composable
private fun InputTelegramDataScreenType(
    userIdText:String,
    botKeyText:String,
    onUserIdTextChanged:(String) -> Unit,
    onBotKeyTextChanged:(String) -> Unit,
    onSaveTelegramData:() -> Unit,
    onWhereToGet:() -> Unit,
    isSaveTelegramDataButtonAvailable:Boolean,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically)
    ) {

        item {
            InputTelegramDataTextField(
                text = userIdText,
                onChange = onUserIdTextChanged,
                label = "UserId"
            )

            InputTelegramDataTextField(
                text = botKeyText,
                onChange = onBotKeyTextChanged,
                label = "Bot key"
            )

            SaveTelegramDataButton(
                onClick = onSaveTelegramData,
                enabled = isSaveTelegramDataButtonAvailable
            )
        }

        item {
            TextButton(
                onClick = onWhereToGet,
            ) {
                Text(text = "Where to get?", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}


@Composable
private fun SaveTelegramDataButton(
    onClick:() -> Unit,
    enabled:Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = enabled
    ) {
        Text(text = "Save")
    }
}

@Composable
private fun InputTelegramDataTextField(
    text:String,
    onChange:(String) -> Unit,
    label:String
) {
    OutlinedTextField(
        value = text,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        label = {
            Text(text = label, style = MaterialTheme.typography.titleMedium)
        },

    )
}

@Composable
private fun LoadingScreenType() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
