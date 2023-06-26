package com.xxmrk888ytxx.wordgroupscreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.WithLocalProviderForPreview
import com.xxmrk888ytxx.wordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordgroupscreen.models.ScreenState

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WordGroupScreen(
    screenState: ScreenState,
    onEvent:(UiEvent) ->  Unit
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
        },
    ) { paddings ->
        
        AnimatedContent(
            targetState = screenState::class,
            modifier = Modifier.padding(paddings)
        ) { state ->
            when(state) {
                ScreenState.EmptyWordGroupState::class -> EmptyWordGroupState(onEvent)

                ScreenState.WordList::class -> {
                    if(screenState is ScreenState.WordList) {
                        WordListState(onEvent,screenState)
                    }
                }
            }
        }
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordListState(onEvent: (UiEvent) -> Unit, screenState: ScreenState.WordList) {
    val navigator = LocalNavigator.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(screenState.wordList, key = { it.id }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { onEvent(LocalUiEvent.OpenWordGroupEvent(navigator,it)) }
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(it.imageUrl != null) {
                        AsyncImage(
                            model = it.imageUrl,
                            contentDescription = "",
                            modifier = Modifier.size(250.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${it.primaryLanguage.name} - ${it.secondaryLanguage.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


            }
        }
    }
}

@Composable
private fun EmptyWordGroupState(onEvent: (UiEvent) -> Unit) {
    val navigator = LocalNavigator.current

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(R.string.you_are_haven_t_word_groups_but_you_can_change_it),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { onEvent(LocalUiEvent.AddFirstWordGroupButtonClickEvent(navigator)) }
        ) {
            Text(text = stringResource(R.string.add_first_word_group))
        }

    }
}

@Composable
@Preview
fun EmptyWordGroupStateWhite() = WithLocalProviderForPreview {
    val screenState = ScreenState.EmptyWordGroupState

    WordGroupScreen(screenState = screenState, onEvent = {})
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
fun EmptyWordGroupStateDark() = WithLocalProviderForPreview {
    val screenState = ScreenState.EmptyWordGroupState

    WordGroupScreen(screenState = screenState, onEvent = {})
}