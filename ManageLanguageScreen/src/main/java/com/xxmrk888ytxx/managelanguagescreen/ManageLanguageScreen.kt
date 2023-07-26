package com.xxmrk888ytxx.managelanguagescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.CreateNewLanguageDialog
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.managelanguagescreen.models.CreateLanguageDialogState
import com.xxmrk888ytxx.managelanguagescreen.models.LocalUiEvent
import com.xxmrk888ytxx.managelanguagescreen.models.ScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageLanguageScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    val isHaveLanguages = remember(screenState.languageList) {
        screenState.languageList.isNotEmpty()
    }

    val navigator = LocalNavigator.current


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Language list")
                },
                navigationIcon = {
                    BackNavigationButton {
                        onEvent(LocalUiEvent.BackScreenEvent(
                            navigator
                        ))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(LocalUiEvent.ShowCreateNewLanguageDialog)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(paddings)
        ) {
            if(isHaveLanguages) {
                items(screenState.languageList, key = { it.id }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = { onEvent(LocalUiEvent.RemoveLanguageEvent(
                                it.id,
                                snackbarHostState,
                                scope
                            )) },
                            enabled = !screenState.isRemovingInProcess
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_delete_24),
                                contentDescription = "",
                            )
                        }
                    }


                }
            } else {
                item {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "You don't have any languages added")
                    }
                }
            }
        }
    }

    if(screenState.createLanguageDialogState is CreateLanguageDialogState.Showed) {
        CreateNewLanguageDialog(
            onHideDialog = { onEvent(LocalUiEvent.HideCreateNewLanguageDialog) },
            newLanguageText = screenState.createLanguageDialogState.languageName,
            onNewLanguageTextInputted = { onEvent(LocalUiEvent.NewLanguageNameEnteredEvent(it)) },
            onNewLanguageCreated = { onEvent(LocalUiEvent.CreateNewLanguage(snackbarHostState,scope)) },
            isAddingInProcess = screenState.createLanguageDialogState.isAddingInProcess
        )
    }
}