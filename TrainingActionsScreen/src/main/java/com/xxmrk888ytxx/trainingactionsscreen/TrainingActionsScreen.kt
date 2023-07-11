package com.xxmrk888ytxx.trainingactionsscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.trainingactionsscreen.models.Actions
import com.xxmrk888ytxx.trainingactionsscreen.models.LocalUiEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceType")
@Composable
fun TrainingActionsScreen(
    onEvent:(UiEvent) -> Unit
) {

    val navigator = LocalNavigator.current

    val actionList = remember(key1 = navigator) {
        persistentListOf(
            Actions(
                text = R.string.word_translation,
                onClick = { onEvent(LocalUiEvent.OpenWordTranslateTraining(navigator)) }
            )
        )
    }
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.select_a_training)) },
            )
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier.padding(paddings)
        ) {
            actionList(
                listActions = actionList
            )
        }
    }
}

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
internal fun LazyListScope.actionList(
    listActions:ImmutableList<Actions>
) {
    items(listActions) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = it.onClick
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = it.text),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
