package com.xxmrk888ytxx.wordgroupscreen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BottomSheetDialog
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.WithLocalProviderForPreview
import com.xxmrk888ytxx.corecompose.theme.ui.theme.models.BottomSheetDialogItem
import com.xxmrk888ytxx.wordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordgroupscreen.models.ScreenState
import com.xxmrk888ytxx.wordgroupscreen.models.WordGroup
import com.xxmrk888ytxx.wordgroupscreen.models.WordGroupDialogOptionState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


/**
 * [Ru]
 * Экран для показа групп слов
 */

/**
 * [En]
 * Screen for view groups of words
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WordGroupScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val selectImageContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { onEvent(LocalUiEvent.OnImagePickedEvent(it)) }
    )

    val navigator = LocalNavigator.current

    val isListEmpty = remember(screenState.wordList) {
        screenState.wordList.isEmpty()
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {

            FloatingActionButton(onClick = {
                onEvent(
                    LocalUiEvent.FloatButtonClickEvent(
                        navigator
                    )
                )
            }) {
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
                    Text(
                        text = stringResource(R.string.groups_of_words),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                scrollBehavior = scrollBehavior,
                windowInsets = WindowInsets(),
                expandedHeight = 0.dp
            )
        }
    ) { paddings ->

        AnimatedContent(
            targetState = isListEmpty,
            modifier = Modifier.padding(paddings),
            label = ""
        ) { isEmpty ->

            if (isEmpty) {
                EmptyWordGroupState(onEvent)
            } else {
                WordListState(
                    onEvent,
                    screenState.wordList,
                    onShowOptionDialog = { wordGroupId, isHaveImage ->
                        onEvent(
                            LocalUiEvent.ShowWordGroupDialogOptionState(
                                wordGroupId,
                                isHaveImage
                            )
                        )
                    }
                )
            }
        }

    }

    if (screenState.wordGroupDialogOptionState is WordGroupDialogOptionState.Showed) {
        WordGroupDialogOption(
            isHaveImage = screenState.wordGroupDialogOptionState.isHaveImage,
            onDismiss = { onEvent(LocalUiEvent.HideWordGroupDialogOption) },
            onRemoveWordGroup = {
                onEvent(LocalUiEvent.RemoveWordGroupEvent(screenState.wordGroupDialogOptionState.wordGroupId))
                onEvent(LocalUiEvent.HideWordGroupDialogOption)
            },
            onRemoveImage = {
                onEvent(LocalUiEvent.RemoveImageEvent(screenState.wordGroupDialogOptionState.wordGroupId))
                onEvent(LocalUiEvent.HideWordGroupDialogOption)
            },
            onAttachImage = {
                onEvent(LocalUiEvent.AttachImageRequest(selectImageContract))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WordListState(
    onEvent: (UiEvent) -> Unit,
    wordList: ImmutableList<WordGroup>,
    onShowOptionDialog: (Int, Boolean) -> Unit,
) {
    val navigator = LocalNavigator.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(wordList, key = { it.id }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .combinedClickable(
                        onClick = {
                            onEvent(
                                LocalUiEvent.OpenWordGroupEvent(
                                    navigator,
                                    it
                                )
                            )
                        },
                        onLongClick = { onShowOptionDialog(it.id, it.imageUrl != null) }
                    ),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (it.imageUrl != null) {
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

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordGroupDialogOption(
    isHaveImage: Boolean,
    onDismiss: () -> Unit,
    onRemoveWordGroup: () -> Unit,
    onRemoveImage: () -> Unit,
    onAttachImage: () -> Unit,
) {

    val context = LocalContext.current

    val imageItem = remember(isHaveImage) {

        if (isHaveImage) {
            BottomSheetDialogItem(
                text = context.getString(R.string.remove_image),
                icon = R.drawable.baseline_hide_image_24,
                onClick = onRemoveImage
            )
        } else {
            BottomSheetDialogItem(
                text = context.getString(R.string.attach_image),
                icon = R.drawable.baseline_image_24,
                onClick = onAttachImage
            )
        }
    }


    BottomSheetDialog(
        onDismiss = onDismiss,
        items = persistentListOf(
            BottomSheetDialogItem(
                text = stringResource(id = R.string.remove),
                icon = R.drawable.baseline_delete_outline_24,
                onClick = onRemoveWordGroup
            ),
            imageItem,

            )
    )
}

@Composable
private fun EmptyWordGroupState(onEvent: (UiEvent) -> Unit) {
    val navigator = LocalNavigator.current

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(R.string.you_are_haven_t_word_groups_but_you_can_change_it),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
        )

        Button(
            onClick = {
                onEvent(
                    LocalUiEvent.AddFirstWordGroupButtonClickEvent(
                        navigator,
                    )
                )
            }
        ) {
            Text(text = stringResource(R.string.add_first_word_group))
        }

    }
}

@Composable
@Preview
fun EmptyWordGroupStateWhite() = WithLocalProviderForPreview {
    val screenState = ScreenState()

    WordGroupScreen(screenState = screenState, onEvent = {})
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE
)
fun EmptyWordGroupStateDark() = WithLocalProviderForPreview {
    val screenState = ScreenState()

    WordGroupScreen(screenState = screenState, onEvent = {})
}