package com.xxmrk888ytxx.viewgroupwordsscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState

@Composable
fun ViewGroupWordsScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val navigator = LocalNavigator.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(LocalUiEvent.FloatButtonClickEvent(navigator)) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddings ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            when (screenState) {
                is ScreenState.EmptyState -> {
                    EmptyState(onEvent)
                }

                is ScreenState.ViewWords -> TODO()
            }
        }
    }
}

@Composable
private fun EmptyState(onEvent: (UiEvent) -> Unit) {

    val navigator = LocalNavigator.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.in_this_group_haven_t_words),
            style = MaterialTheme.typography.titleLarge
        )

        Button(onClick = { onEvent(LocalUiEvent.ClickButtonForAddNewWordOnEmptyStateEvent(navigator)) }) {
            Text(text = stringResource(R.string.add_new_word))
        }

    }
}
