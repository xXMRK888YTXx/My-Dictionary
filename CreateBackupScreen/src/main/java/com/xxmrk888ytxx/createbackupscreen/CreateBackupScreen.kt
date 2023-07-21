package com.xxmrk888ytxx.createbackupscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.createbackupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createbackupscreen.models.ScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBackupScreen(
    screenState: ScreenState,
    onEvent:(UiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.create_backup))
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { onEvent(LocalUiEvent.SelectBackupPlaceEvent) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = stringResource(R.string.choose_backup_place))
            }
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            item {
                Text(
                    text = stringResource(R.string.select_a_groups_of_words_to_be_include_in_the_backup),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            items(screenState.availableWordGroups) { wordGroup ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = wordGroup.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Switch(
                        checked = screenState.selectedWordGroupId.contains(wordGroup.id),
                        onCheckedChange = { onEvent(LocalUiEvent.ChangeWordGroupSelectStateEvent(wordGroup.id)) }
                    )
                }
            }
        }
    }
}