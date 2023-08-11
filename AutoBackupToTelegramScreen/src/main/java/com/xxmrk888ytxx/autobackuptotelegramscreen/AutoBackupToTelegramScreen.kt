package com.xxmrk888ytxx.autobackuptotelegramscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.BackupTime
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.LocalUiEvent
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.ScreenState
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.ScreenType
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AutoBackupToTelegramScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
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
            when (screenState.screenType) {
                ScreenType.INPUT_TELEGRAM_DATA -> {
                    InputTelegramDataScreenType(
                        userIdText = screenState.userIdText,
                        botKeyText = screenState.botKeyText,
                        onUserIdTextChanged = { onEvent(LocalUiEvent.UserIdTextChangedEvent(it)) },
                        onBotKeyTextChanged = { onEvent(LocalUiEvent.BotKeyTextChangedEvent(it)) },
                        onSaveTelegramData = {
                            onEvent(
                                LocalUiEvent.SaveTelegramDataEvent(
                                    snackbarHostState
                                )
                            )
                        },
                        onWhereToGet = { onEvent(LocalUiEvent.WhereToGetEvent) },
                        isSaveTelegramDataButtonAvailable = screenState.isSaveTelegramDataAvailable,
                    )
                }

                ScreenType.BACKUP_SETTINGS -> BackupSettingsScreenType(
                    isAutoBackupEnabled = screenState.backupSettings.isEnabled,
                    onBackupStateChanged = { onEvent(LocalUiEvent.BackupStateChanged(it)) },
                    backupTime = screenState.backupSettings.backupTime,
                    onChangeBackupTime = { onEvent(LocalUiEvent.BackupTimeChangedEvent(it)) },
                    isNotExecuteIfNotChanges = screenState.backupSettings.isNotExecuteIfNotChanges,
                    onIsNotExecuteIfNotChangesStateChanged = { onEvent(LocalUiEvent.IsNotExecuteIfNotChangesStateChangedEvent(it)) },
                    onCreateBackupNow = { onEvent(LocalUiEvent.CreateBackup) },
                    onRemoveTelegramData = { onEvent(LocalUiEvent.RemoveTelegramData) }
                )

                ScreenType.LOADING -> LoadingScreenType()
            }
        }
    }
}

@Composable
fun BackupSettingsScreenType(
    isAutoBackupEnabled:Boolean,
    onBackupStateChanged:(Boolean) -> Unit,
    backupTime: BackupTime,
    onChangeBackupTime:(BackupTime) -> Unit,
    isNotExecuteIfNotChanges:Boolean,
    onIsNotExecuteIfNotChangesStateChanged:(Boolean) -> Unit,
    onCreateBackupNow:() -> Unit,
    onRemoveTelegramData:() -> Unit

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SwitchParam(
                text = "Enable auto backup to Telegram",
                isChecked = isAutoBackupEnabled,
                onStateChanged = onBackupStateChanged,
            )
        }

        item {
            SwitchParam(
                text = "Do not backup if there are no changes",
                isChecked = isNotExecuteIfNotChanges,
                onStateChanged = onIsNotExecuteIfNotChangesStateChanged,
                isEnabled = isAutoBackupEnabled
            )
        }

        
        item {
            Text(text = "Backup frequency", modifier = Modifier.padding(bottom = 10.dp))

            SelectTimeWidget(
                selectedTime = backupTime,
                onBackupTimeChanged = onChangeBackupTime
            )
        }

        item {
            ActionButton(
                text = "Create backup now",
                onClick = onCreateBackupNow
            )
        }

        item {
            ActionButton(
                text = "Remove telegram data",
                onClick = onRemoveTelegramData
            )
        }
    }
}

@Composable
fun ActionButton(
    text:String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectTimeWidget(
    selectedTime:BackupTime,
    onBackupTimeChanged:(BackupTime) -> Unit
) {
    data class SelectTimeWidgetItem(
        val title:String,
        val linkedBackupTime:BackupTime
    )

    val items = remember {
        persistentListOf(
            SelectTimeWidgetItem(
                title = "6 hours",
                linkedBackupTime = BackupTime.HOURS_6
            ),
            SelectTimeWidgetItem(
                title = "12 hours",
                linkedBackupTime = BackupTime.HOURS_12
            ),
            SelectTimeWidgetItem(
                title = "1 day",
                linkedBackupTime = BackupTime.DAY_1
            ),
            SelectTimeWidgetItem(
                title = "1 week",
                linkedBackupTime = BackupTime.WEEK_1
            ),
        )
    }


    LazyRow(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) {
            FilterChip(
                selected = selectedTime == it.linkedBackupTime,
                onClick = { onBackupTimeChanged(it.linkedBackupTime) },
                label = { 
                    Text(text = it.title)
                },
                enabled = selectedTime != BackupTime.NONE
            )
        }
    }
}

@Composable
private fun SwitchParam(
    text:String,
    isChecked:Boolean,
    onStateChanged:(Boolean) -> Unit,
    isEnabled:Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = isChecked,
            onCheckedChange = onStateChanged,
            enabled = isEnabled
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    screenType: ScreenType,
    onBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when (screenType) {
                    ScreenType.INPUT_TELEGRAM_DATA -> "Enter telegram data"
                    ScreenType.BACKUP_SETTINGS -> "Backup settings"
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
    userIdText: String,
    botKeyText: String,
    onUserIdTextChanged: (String) -> Unit,
    onBotKeyTextChanged: (String) -> Unit,
    onSaveTelegramData: () -> Unit,
    onWhereToGet: () -> Unit,
    isSaveTelegramDataButtonAvailable: Boolean,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
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
    onClick: () -> Unit,
    enabled: Boolean,
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
    text: String,
    onChange: (String) -> Unit,
    label: String,
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
