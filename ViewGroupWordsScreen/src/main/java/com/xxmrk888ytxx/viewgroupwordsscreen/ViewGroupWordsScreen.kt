package com.xxmrk888ytxx.viewgroupwordsscreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BottomSheetDialog
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalAdController
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.models.BottomSheetDialogItem
import com.xxmrk888ytxx.viewgroupwordsscreen.models.LocalUiEvent
import com.xxmrk888ytxx.viewgroupwordsscreen.models.ScreenState
import com.xxmrk888ytxx.viewgroupwordsscreen.models.SearchState
import com.xxmrk888ytxx.viewgroupwordsscreen.models.Word
import com.xxmrk888ytxx.viewgroupwordsscreen.models.WordOptionDialogState
import kotlinx.collections.immutable.persistentListOf



/**
 * [Ru]
 * Экран для показа слов из группы слов
 */

/**
 * [En]
 * Screen for view words from group of words
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ViewGroupWordsScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val navigator = LocalNavigator.current

    val isWordListEmpty = remember(screenState.words) {
        screenState.words.isEmpty()
    }

    val adController = LocalAdController.current

    val lazyListState = rememberLazyListState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
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

            Column {

                CenterAlignedTopAppBar(
                    title = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                10.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = screenState.wordGroupInfo.name,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = "${screenState.wordGroupInfo.primaryLanguageName} -" +
                                        " ${screenState.wordGroupInfo.secondaryLanguageName}",
                                style = MaterialTheme.typography.bodyLarge
                            )


                        }
                    },
                    navigationIcon = {
                        BackNavigationButton {
                            onEvent(LocalUiEvent.OnBackScreenEvent(navigator))
                        }
                    },
                    actions = {
                        IconButton(onClick = { onEvent(LocalUiEvent.ChangeSearchStateEvent()) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_search_24),
                                contentDescription = "",
                                modifier =  Modifier.size(32.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior

                )


                AnimatedVisibility(visible = screenState.searchState is SearchState.Enabled) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val text = remember(screenState.searchState) {
                                (screenState.searchState as? SearchState.Enabled)?.searchValue ?: ""
                            }

                            Text(
                                text = stringResource(R.string.enter_the_search_text),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )


                            OutlinedTextField(
                                value = text,
                                onValueChange = { onEvent(LocalUiEvent.OnChangeSearchValueEvent(it)) },
                                label = { Text(
                                    text = stringResource(R.string.search),
                                    style = MaterialTheme.typography.titleMedium
                                ) },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }


        },
        bottomBar = {
            adController.WordGroupScreenBanner()
        },

    ) { paddings ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {

            if (isWordListEmpty) {
                EmptyState(onEvent,screenState.searchState is SearchState.Enabled)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(rememberNestedScrollInteropConnection()),
                    state = lazyListState
                ) {

                    items(screenState.words, key = { it.id }) {
                        Box(modifier = Modifier.animateItemPlacement()) {
                            WordItem(
                                word = it,
                                onTextToSpeechRequest = { text ->
                                    onEvent(LocalUiEvent.TextToSpeechEvent(text))
                                },
                                onOpenWordForEdit = { wordId ->
                                    onEvent(LocalUiEvent.OpenWordForEditEvent(navigator, wordId))
                                },
                                onOpenWordOptionDialog = { wordId ->
                                    onEvent(LocalUiEvent.ShowWordOptionDialogEvent(wordId))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (screenState.wordOptionDialogState is WordOptionDialogState.Showed) {
        WordOptionDialog(
            onDismiss = {
                onEvent(LocalUiEvent.HideWordOptionDialogEvent)
            },
            onRemoveWord = {
                onEvent(LocalUiEvent.RemoveWordEvent(screenState.wordOptionDialogState.wordId))
                onEvent(LocalUiEvent.HideWordOptionDialogEvent)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun WordItem(
    word: Word,
    onTextToSpeechRequest: (String) -> Unit,
    onOpenWordForEdit: (Int) -> Unit,
    onOpenWordOptionDialog: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .combinedClickable(
                onClick = {
                    onOpenWordForEdit(word.id)
                },
                onLongClick = {
                    onOpenWordOptionDialog(word.id)
                }
            ),
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

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_volume_up_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onTextToSpeechRequest(word.wordText) }
                )
            }
        }
    }
}

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordOptionDialog(
    onDismiss: () -> Unit,
    onRemoveWord: () -> Unit,
) {
    BottomSheetDialog(
        onDismiss = onDismiss,
        items = persistentListOf(
            BottomSheetDialogItem(
                text = stringResource(id = R.string.remove),
                icon = R.drawable.baseline_delete_outline_24,
                onClick = onRemoveWord
            )
        )
    )
}

@Composable
private fun EmptyState(
    onEvent: (UiEvent) -> Unit,
    isSearchState:Boolean
) {

    val navigator = LocalNavigator.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if(isSearchState) stringResource(R.string.nothing_found) else stringResource(R.string.in_this_group_haven_t_words),
            style = MaterialTheme.typography.titleLarge
        )

        if(!isSearchState) {
            Button(onClick = { onEvent(LocalUiEvent.ClickButtonForAddNewWordOnEmptyStateEvent(navigator)) }) {
                Text(text = stringResource(R.string.add_new_word))
            }
        }

    }
}
