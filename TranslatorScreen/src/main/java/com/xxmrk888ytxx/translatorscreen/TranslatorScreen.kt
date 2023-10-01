package com.xxmrk888ytxx.translatorscreen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.IdRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.xxmrk888ytxx.coreandroid.ActivityContracts.SpeechRecognizeContract.SpeechRecognizeContract
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.translatorscreen.models.ChangeLanguageBottomSheetState
import com.xxmrk888ytxx.translatorscreen.models.FastAddWordInDictionaryBottomSheetState
import com.xxmrk888ytxx.translatorscreen.models.LoadingModelsDialogState
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import com.xxmrk888ytxx.translatorscreen.models.SupportedLanguage
import com.xxmrk888ytxx.translatorscreen.models.TranslateState
import com.xxmrk888ytxx.translatorscreen.models.WordGroup
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val clipboardManager = LocalClipboardManager.current


    val context = LocalContext.current

    val uiScope = rememberCoroutineScope()

    val scope = rememberCoroutineScope()

    val speechRecognizeContract = rememberLauncherForActivityResult(
        contract = SpeechRecognizeContract(),
        onResult = { recognizedText ->
            recognizedText?.let { onEvent(LocalUiEvent.SpeechRecognizedEvent(it)) }
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(LocalConfiguration.current.screenHeightDp.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.translator),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            SelectLanguagesForTranslateWidget(
                onOpenSelectLanguageDialogForOriginalLanguage = { onEvent(LocalUiEvent.ShowListForChangeOriginalLanguage) },
                originLanguageName = stringResource(id = screenState.currentOriginalLanguage.name),
                exchangeLanguages = { onEvent(LocalUiEvent.ExchangeLanguages) },
                onOpenSelectLanguageDialogForLanguageForTranslate = { onEvent(LocalUiEvent.ShowListForChangeLanguageForTranslate) },
                languageForTranslateName = stringResource(id = screenState.currentLanguageForTranslate.name)
            )



            TranslateCardForInputText(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                text = screenState.textForTranslate,
                onChangeText = { onEvent(LocalUiEvent.TextForTranslateInput(it)) },
                onClear = { onEvent(LocalUiEvent.ClearTextForTranslate) },
                onAskText = { onEvent(LocalUiEvent.AskTextForTranslateEvent) },
                onPastFromClipboard = {
                    if (clipboardManager.hasText()) onEvent(
                        LocalUiEvent.PastTextFromClipboard(
                            clipboardManager.getText()?.text
                        )
                    )
                },
                onDetectTextByCamera = { },
                onRecognizeVoice = {
                    onEvent(
                        LocalUiEvent.RequestRecognizeSpeechForTextToTranslate(
                            speechRecognizeContract,
                            snackbarHostState,
                            context,
                            uiScope
                        )
                    )
                }
            )

            TranslationResult(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .weight(1f),
                screenState.translateState,
                onAskText = { onEvent(LocalUiEvent.AskTranslatedTextEvent) },
                onCopyText = { onEvent(LocalUiEvent.CopyTranslatedTextingBuffer(clipboardManager)) },
                onFastAddWordInDictionary = { onEvent(LocalUiEvent.ShowFastAddWordInDictionaryBottomSheet) }
            )
        }

        if (screenState.changeLanguageBottomSheetState !is ChangeLanguageBottomSheetState.Hidden) {
            ModalBottomSheet(
                onDismissRequest = {
                    onEvent(LocalUiEvent.BottomSheetDismissRequest)
                },
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                )
            ) {
                LanguageSelectList(
                    languageList = screenState.supportedLanguageList,
                    onLanguageSelected = { onEvent(LocalUiEvent.ChangeSelectedLanguage(it)) }
                )
            }
        }

        if (screenState.loadingModelsDialogState !is LoadingModelsDialogState.Hidden) {
            LoadingModelsDialogStateDialog(
                screenState.loadingModelsDialogState,
                onDismiss = { onEvent(LocalUiEvent.DismissLoadingModelsDialogStateDialog) },
                onRequestToDownloadModel = {
                    onEvent(
                        LocalUiEvent.RequestToDownloadModelsForTranslate(
                            snackbarHostState,
                            context.applicationContext,
                            uiScope
                        )
                    )
                }
            )
        }

        if (screenState.fastAddWordInDictionaryBottomSheetState is FastAddWordInDictionaryBottomSheetState.Showed) {
            val state = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            )

            FastAddWordInDictionaryBottomSheet(
                sheetState = state,
                state = screenState.fastAddWordInDictionaryBottomSheetState,
                availableWordGroups = screenState.availableWordGroups,
                onDismiss = {
                    onEvent(LocalUiEvent.DismissFastAddWordInDictionaryBottomSheet)
                },
                onCompleteAdding = {

                },
                onUpdateDialogState = {
                    onEvent(LocalUiEvent.UpdateStateForFastAddWordInDictionaryBottomSheet(it))
                }
            )
        }
    }
}

@Composable
fun ColumnScope.SelectLanguagesForTranslateWidget(
    onOpenSelectLanguageDialogForOriginalLanguage:() -> Unit,
    originLanguageName:String,
    exchangeLanguages:() -> Unit,
    onOpenSelectLanguageDialogForLanguageForTranslate:() -> Unit,
    languageForTranslateName:String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                start = 40.dp,
                end = 40.dp,
                bottom = 10.dp
            )
    ) {
        TextButton(onClick = onOpenSelectLanguageDialogForOriginalLanguage) {
            Text(text = originLanguageName)
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { exchangeLanguages() }) {
            Icon(
                painter = painterResource(
                    id = R.drawable.baseline_compare_arrows_24
                ),
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onOpenSelectLanguageDialogForLanguageForTranslate) {
            Text(text = languageForTranslateName)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun FastAddWordInDictionaryBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    state: FastAddWordInDictionaryBottomSheetState.Showed,
    availableWordGroups: ImmutableList<WordGroup>,
    onDismiss: () -> Unit,
    onCompleteAdding: () -> Unit,
    onUpdateDialogState: (FastAddWordInDictionaryBottomSheetState.Showed) -> Unit,
) {

    @Composable
    fun EditWordInfoState() {
        val label = remember<@Composable (String) -> Unit> {
            {
                Text(text = it, style = MaterialTheme.typography.bodyLarge)
            }
        }

        OutlinedTextField(
            value = state.originalWord,
            onValueChange = { text -> onUpdateDialogState(state.copy(originalWord = text)) },
            label = { label(stringResource(R.string.word)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.translation,
            onValueChange = { text -> onUpdateDialogState(state.copy(translation = text)) },
            label = { label(stringResource(R.string.translate_of_word)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.transcription,
            onValueChange = { text -> onUpdateDialogState(state.copy(transcription = text)) },
            label = { label(stringResource(R.string.transcription_of_word)) },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.selected_group_of_word))

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = { onUpdateDialogState(state.copy(isSelectingWordGroupState = true)) }) {
                Text(
                    text = state.selectedWordGroup?.name ?: stringResource(R.string.not_selected)
                )
            }
        }
    }

    @Composable
    fun SelectWordGroupState() {
        if (availableWordGroups.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(availableWordGroups) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            onUpdateDialogState(
                                state.copy(
                                    selectedWordGroup = it,
                                    isSelectingWordGroupState = false
                                )
                            )
                        }
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.you_are_haven_t_a_group_of_words),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(R.string.you_need_to_add_group_of_words_and_come_back_here),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = {
                            if (state.isSelectingWordGroupState) onUpdateDialogState(
                                state.copy(
                                    isSelectingWordGroupState = false
                                )
                            )
                            else onDismiss()
                        }) {
                            Icon(
                                painter = if (state.isSelectingWordGroupState) painterResource(id = R.drawable.baseline_arrow_back_24)
                                else painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    actions = {
                        if (!state.isSelectingWordGroupState) {
                            Button(
                                onClick = onCompleteAdding,
                                enabled = state.selectedWordGroup != null
                            ) {
                                Text(text = stringResource(id = R.string.save))
                            }
                        }
                    },
                    modifier = Modifier.padding(start = 10.dp, end = 16.dp)
                )
            }
        ) { paddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddings),
            ) {
                AnimatedContent(targetState = state.isSelectingWordGroupState, label = "") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (it) {
                            SelectWordGroupState()
                        } else {
                            EditWordInfoState()
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingModelsDialogStateDialog(
    loadingModelsDialogState: LoadingModelsDialogState,
    onDismiss: () -> Unit,
    onRequestToDownloadModel: () -> Unit,
) {

    val isCanDismiss = remember(loadingModelsDialogState) {
        when (loadingModelsDialogState) {
            LoadingModelsDialogState.Error -> true
            LoadingModelsDialogState.Hidden -> true
            LoadingModelsDialogState.Loading -> false
            LoadingModelsDialogState.OfferToDownload -> true
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = isCanDismiss,
            dismissOnClickOutside = isCanDismiss
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (loadingModelsDialogState) {

                    LoadingModelsDialogState.Error -> {
                        Text(
                            text = stringResource(R.string.an_error_occurred_while_models_for_translate_loading),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = stringResource(R.string.check_your_internet_connection_and_retry_again),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    LoadingModelsDialogState.Hidden -> {}

                    LoadingModelsDialogState.Loading -> {
                        Text(text = stringResource(id = R.string.please_wait))

                        LinearProgressIndicator()
                    }

                    LoadingModelsDialogState.OfferToDownload -> {
                        Text(
                            text = stringResource(R.string.download_required),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = stringResource(R.string.for_translating_to_selected_your_languages_needed_to_download_models_for_translate_weight_of_models_about_30_60_mb),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }

                Row(
                    Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                ) {
                    when (loadingModelsDialogState) {

                        LoadingModelsDialogState.Error -> {
                            TextButton(onClick = onDismiss) {
                                Text(text = stringResource(R.string.got_it))
                            }
                        }

                        LoadingModelsDialogState.Hidden -> {}

                        LoadingModelsDialogState.Loading -> {}

                        LoadingModelsDialogState.OfferToDownload -> {

                            TextButton(onClick = onDismiss) {
                                Text(text = stringResource(R.string.cancel))
                            }

                            TextButton(onClick = onRequestToDownloadModel) {
                                Text(text = stringResource(R.string.download))
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ColumnScope.TranslationResult(
    modifier: Modifier = Modifier,
    translateState: TranslateState,
    onAskText: () -> Unit,
    onCopyText: () -> Unit,
    onFastAddWordInDictionary: () -> Unit,
) {

    data class Action(
        @IdRes val icon: Int,
        val onClick: () -> Unit,
    )

    val actions = remember {
        persistentListOf(
            Action(
                icon = R.drawable.baseline_content_copy_24,
                onClick = onCopyText
            ),
            Action(
                icon = R.drawable.baseline_playlist_add_24,
                onClick = onFastAddWordInDictionary,
            ),
            Action(
                icon = R.drawable.baseline_volume_up_24,
                onClick = onAskText,
            ),
        )
    }

    AnimatedContent(
        targetState = translateState,
        label = "",
        modifier = modifier
    ) { state ->
        when (state) {

            is TranslateState.Error -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.an_error_occurred_while_translating_a_word),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = stringResource(id = state.errorText),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            TranslateState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(text = stringResource(R.string.please_wait))

                    LinearProgressIndicator()
                }
            }

            TranslateState.None -> {}

            is TranslateState.Translated -> {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        text = state.translatedText,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 25.sp
                        )
                    )

                    LazyColumn {
                        items(actions) {
                            IconButton(onClick = it.onClick) {
                                Icon(
                                    painter = painterResource(id = it.icon),
                                    contentDescription = "",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ColumnScope.TranslateCardForInputText(
    modifier: Modifier = Modifier,
    text: String,
    onChangeText: (String) -> Unit,
    onClear: () -> Unit,
    onAskText: () -> Unit,
    onPastFromClipboard: () -> Unit,
    onDetectTextByCamera: () -> Unit,
    onRecognizeVoice: () -> Unit,
) {
    data class Action(
        @IdRes val icon: Int,
        val isEnabled: Boolean = true,
        val onClick: () -> Unit,
    )

    val isEmpty = remember(key1 = text) {
        text.isEmpty()
    }

    val textForTranslateActions = remember(isEmpty) {
        persistentListOf(
            Action(
                icon = R.drawable.baseline_close_24,
                onClick = onClear,
                isEnabled = !isEmpty
            ),
            Action(
                icon = R.drawable.baseline_volume_up_24,
                onClick = onAskText,
                isEnabled = !isEmpty
            ),
            Action(
                icon = R.drawable.baseline_content_paste_24,
                onClick = onPastFromClipboard
            ),
            Action(
                icon = R.drawable.baseline_photo_camera_24,
                onClick = onDetectTextByCamera
            ),
            Action(
                icon = R.drawable.baseline_keyboard_voice_24,
                onClick = onRecognizeVoice
            )
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            20.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = onChangeText,
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    color = if (isSystemInDarkTheme()) Color(0xFFC2C9BD) else Color(0xFF424940),
                    fontSize = 22.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 10.dp,
                        start = 10.dp
                    ),
                decorationBox = {
                    Column {
                        Row(
                            modifier = Modifier
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Top)
                                    .weight(1f)
                            ) {
                                if (isEmpty) {
                                    Text(
                                        text = stringResource(R.string.enter_the_text_for_translate),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }

                                it()
                            }

                            LazyColumn(
                                modifier = Modifier
                                    .animateContentSize(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                items(textForTranslateActions, key = { it.icon }) {
                                    IconButton(
                                        onClick = it.onClick,
                                        enabled = it.isEnabled
                                    ) {
                                        Icon(
                                            painter = painterResource(id = it.icon),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            )

        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ColumnScope.LanguageSelectList(
    languageList: List<SupportedLanguage>,
    onLanguageSelected: (SupportedLanguage) -> Unit,
) {
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val searchedList = remember(languageList, searchText) {
        if (searchText.isEmpty()) languageList
        else languageList.filter {
            context.getString(it.name).lowercase().contains(searchText.lowercase())
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp
                ),
            label = {
                Text(
                    text = stringResource(R.string.search),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Black.copy(0f),
                unfocusedIndicatorColor = Color.Black.copy(0f)

            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(searchedList) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable {
                            onLanguageSelected(it)
                        }
                ) {
                    Text(text = stringResource(id = it.name))
                }
            }
        }
    }
}