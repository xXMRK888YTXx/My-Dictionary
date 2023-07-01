package com.xxmrk888ytxx.viewgroupwordsscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState
import com.xxmrk888ytxx.viewgroupwordsscreen.models.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewGroupWordsScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val navigator = LocalNavigator.current

    val isWordListEmpty = remember(screenState.words) {
        screenState.words.isEmpty()
    }

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
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp,Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = screenState.wordGroupInfo.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(text = "${screenState.wordGroupInfo.primaryLanguageName} -" +
                                " ${screenState.wordGroupInfo.secondaryLanguageName}",
                            style = MaterialTheme.typography.bodyLarge
                        )




                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(LocalUiEvent.OnBackScreenEvent(navigator))
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(28.dp)
                        )
                    }
                }
            )
        }
    ) { paddings ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {

            if(isWordListEmpty) {
                EmptyState(onEvent)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(screenState.words, key = { it.id }) {
                        WordItem(word = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun WordItem(word:Word) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = word.wordText,
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                )
            )

            Text(
                text = word.translateText,
                style = TextStyle(
                    fontWeight = FontWeight.W400,
                    fontSize = 18.sp,
                )
            )

            if (word.transcriptionText.isNotEmpty()) {
                Text(
                    text = "[" + word.transcriptionText + "]",
                    style = TextStyle(
                        fontWeight = FontWeight.W300,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    ),

                    )
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
