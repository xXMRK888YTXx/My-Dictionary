package com.xxmrk888ytxx.createwordgroupscreen

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.WithLocalProviderForPreview
import com.xxmrk888ytxx.createwordgroupscreen.models.CreateNewLanguageDialogState
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.createwordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createwordgroupscreen.models.Pages
import com.xxmrk888ytxx.createwordgroupscreen.models.ScreenState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateWordGroupScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {
    val pages = remember {
        Pages.values()
    }

    val pagerState = rememberPagerState()

    val scope = rememberCoroutineScope()

    val navigator = LocalNavigator.current

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (pages[pagerState.currentPage]) {
                            Pages.ENTER_GROUP_NAME -> stringResource(R.string.enter_group_name)
                            Pages.SELECT_LANGUAGES -> stringResource(R.string.select_languages)
                            Pages.SELECT_IMAGE -> stringResource(R.string.select_image)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(
                            LocalUiEvent.BackPageEvent(
                                pagerState,
                                scope,
                                navigator
                            )
                        )
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
                .padding(paddings),
        ) {

            HorizontalPager(
                pageCount = pages.size,
                userScrollEnabled = false,
                state = pagerState
            ) { currentPage ->

                when (pages[currentPage]) {

                    Pages.ENTER_GROUP_NAME -> {
                        EnterGroupNameState(
                            titleText = screenState.newGroupName,
                            onTitleTextChanged = {
                                onEvent(LocalUiEvent.TitleTextChangedEvent(it))
                            },
                            onNameInputCompleted = {
                                onEvent(
                                    LocalUiEvent.WordGroupNameInputCompletedEvent(
                                        pagerState,
                                        scope
                                    )
                                )
                            }
                        )
                    }

                    Pages.SELECT_LANGUAGES -> {
                        SelectLanguagesState(
                            screenState.languages,
                            primaryLanguage = screenState.selectedPrimaryLanguage,
                            secondaryLanguage = screenState.selectedSecondaryLanguage,
                            onSelectPrimaryLanguage = {
                                onEvent(
                                    LocalUiEvent.SelectNewPrimaryLanguageEvent(
                                        it
                                    )
                                )
                            },
                            onSelectSecondaryLanguage = {
                                onEvent(
                                    LocalUiEvent.SelectNewSecondaryLanguageEvent(
                                        it
                                    )
                                )
                            },
                            onNewLanguageRequest = { onEvent(LocalUiEvent.ShowCreateNewLanguageDialogEvent) },
                            onLanguageSelectCompleted = {
                                onEvent(
                                    LocalUiEvent.LanguageSelectCompletedEvent(
                                        pagerState,
                                        scope
                                    )
                                )
                            }
                        )
                    }

                    Pages.SELECT_IMAGE -> {
                        SelectImageState(
                            screenState.imageGroupUrl,
                            onImageSelectCompleted = {
                                onEvent(
                                    LocalUiEvent.WordGroupCreateCompleted(
                                        navigator
                                    )
                                )
                            },
                            onImageSelected = { onEvent(LocalUiEvent.ImagePickedEvent(it)) },
                            onPickImageRequest = {
                                onEvent(LocalUiEvent.PickImageRequestEvent(it))
                            },
                            isAddWordGroupInProcess = screenState.isAddWordGroupInProcess

                        )
                    }
                }
            }

        }
    }

    if (screenState.createNewLanguageDialogState is CreateNewLanguageDialogState.Showed) {
        CreateNewLanguageDialog(
            onHideDialog = { onEvent(LocalUiEvent.HideCreateNewLanguageDialogEvent) },
            newLanguageText = screenState.createNewLanguageDialogState.newLanguageText,
            onNewLanguageTextInputted = { onEvent(LocalUiEvent.InputTextForLanguageNameEvent(it)) },
            onNewLanguageCreated = { onEvent(LocalUiEvent.ConfigurationNewLanguageCompletedEvent) },
            isAddingInProcess = screenState.createNewLanguageDialogState.isAddingInProcess
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SelectImageState(
    selectedImageUrl: String?,
    onImageSelectCompleted: () -> Unit,
    onImageSelected: (Uri?) -> Unit,
    onPickImageRequest: (ActivityResultLauncher<PickVisualMediaRequest>) -> Unit,
    isAddWordGroupInProcess: Boolean,
) {
    val selectImageContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = onImageSelected
    )

    Scaffold(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        bottomBar = {

            if (isAddWordGroupInProcess) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                Button(
                    onClick = onImageSelectCompleted,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.finish))
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.image_animation))

            val progress by animateLottieCompositionAsState(
                composition,
                restartOnPlay = true,
                speed = 0.6f,
                iterations = 5,
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            Text(
                text = stringResource(R.string.you_can_add_image_to_you_word_group_it_is_optional_but_make_your_group_more_beautiful),
                style = MaterialTheme.typography.titleLarge
            )


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.large),
                contentAlignment = Alignment.Center
            ) {
                Scaffold(
                    Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = { onPickImageRequest(selectImageContract) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_image_search_24),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                ) { padding ->

                    AnimatedContent(
                        targetState = selectedImageUrl,
                        transitionSpec = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(700)
                            ) with slideOutHorizontally(
                                targetOffsetX = { 0 },
                                animationSpec = tween(0)
                            )
                        }
                    ) { isImageSelected ->
                        if (isImageSelected != null) {
                            AsyncImage(
                                model = selectedImageUrl,
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(padding),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.onSecondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.baseline_image_24
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }

                    }
                }

            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SelectLanguagesState(
    availableLanguage: ImmutableList<Language>,
    primaryLanguage: Language?,
    secondaryLanguage: Language?,
    onSelectPrimaryLanguage: (Language) -> Unit,
    onSelectSecondaryLanguage: (Language) -> Unit,
    onNewLanguageRequest: () -> Unit,
    onLanguageSelectCompleted: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.translate_language_animation))

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = true,
        speed = 0.6f,
        iterations = 5,
    )

    Scaffold(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        bottomBar = {
            Button(
                onClick = onLanguageSelectCompleted,
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = primaryLanguage != null && secondaryLanguage != null
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    ) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            Text(text = stringResource(R.string.choose_the_language_you_will_translate_into))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(availableLanguage, key = { it.id }) {
                    Box(modifier = Modifier.animateItemPlacement()) {
                        FilterChip(
                            selected = it.id == primaryLanguage?.id,
                            onClick = { onSelectPrimaryLanguage(it) },
                            label = { Text(text = it.name) },
                            enabled = it.id != secondaryLanguage?.id,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                }

                buttonForAddNewLanguage(onNewLanguageRequest)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.choose_the_language_on_which_you_will_translate))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                items(availableLanguage) {
                    FilterChip(
                        selected = it.id == secondaryLanguage?.id,
                        onClick = { onSelectSecondaryLanguage(it) },
                        label = { Text(text = it.name) },
                        enabled = it.id != primaryLanguage?.id,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }

                buttonForAddNewLanguage(onNewLanguageRequest)
            }
        }
    }
}

private fun LazyListScope.buttonForAddNewLanguage(onNewLanguageRequest: () -> Unit) {
    item(key = -1) {
        SuggestionChip(
            onClick = onNewLanguageRequest,
            label = {
                Text(text = stringResource(R.string.add_language))
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
            }
        )
    }
}

@Composable
fun EnterGroupNameState(
    titleText: String,
    onTitleTextChanged: (String) -> Unit,
    onNameInputCompleted: () -> Unit,
) {
    Scaffold(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        bottomBar = {
            Button(
                onClick = onNameInputCompleted,
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = titleText.isNotEmpty()
            ) {
                Text(text = stringResource(R.string.next))
            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.input_text_animation))
            val progress by animateLottieCompositionAsState(
                composition,
                restartOnPlay = true,
                speed = 0.6f,
                iterations = 5,
            )

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            Text(
                text = stringResource(R.string.enter_name_of_new_word_group),
                style = MaterialTheme.typography.titleLarge
            )


            OutlinedTextField(
                value = titleText,
                onValueChange = onTitleTextChanged,
                label = { Text(text = stringResource(R.string.group_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateNewLanguageDialog(
    onHideDialog: () -> Unit,
    newLanguageText: String,
    onNewLanguageTextInputted: (String) -> Unit,
    onNewLanguageCreated: () -> Unit,
    isAddingInProcess: Boolean,
) {
    AlertDialog(onDismissRequest = onHideDialog) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = stringResource(R.string.input_language_name), style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(
                    value = newLanguageText,
                    onValueChange = onNewLanguageTextInputted,
                    label = { Text(text = stringResource(R.string.language_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isAddingInProcess
                )

                Row(
                    Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                ) {

                    if (!isAddingInProcess) {
                        TextButton(onClick = onHideDialog) {
                            Text(text = stringResource(R.string.cancel))
                        }

                        TextButton(
                            onClick = onNewLanguageCreated,
                            enabled = newLanguageText.isNotEmpty()
                        ) {
                            Text(text = stringResource(R.string.add))
                        }
                    } else {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }


            }
        }
    }
}

//Preview

@Preview
@Composable
fun CreateNewLanguageDialogPrevWhite() = WithLocalProviderForPreview {
    CreateNewLanguageDialog(
        onHideDialog = {},
        newLanguageText = "",
        onNewLanguageCreated = {},
        onNewLanguageTextInputted = {},
        isAddingInProcess = false
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun CreateNewLanguageDialogPrevDark() = WithLocalProviderForPreview {
    CreateNewLanguageDialog(
        onHideDialog = {},
        newLanguageText = "",
        onNewLanguageCreated = {},
        onNewLanguageTextInputted = {},
        isAddingInProcess = false
    )
}

@Preview
@Composable
fun EnterGroupNameStatePrevWhite() = WithLocalProviderForPreview {

    EnterGroupNameState(
        titleText = "",
        {},
        {}
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun EnterGroupNameStatePrevDark() = WithLocalProviderForPreview {

    EnterGroupNameState(
        titleText = "",
        {},
        {}
    )
}

@Preview
@Composable
fun SelectLanguagesStatePrevWhite() = WithLocalProviderForPreview {

    SelectLanguagesState(
        availableLanguage = persistentListOf(Language(0,"English"),Language(1,"Russian")),
        primaryLanguage = null,
        secondaryLanguage = Language(1,"Russian"),
        {},{},{},{}
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SelectLanguagesStatePrevDark() = WithLocalProviderForPreview {

    SelectLanguagesState(
        availableLanguage = persistentListOf(Language(0,"English"),Language(1,"Russian")),
        primaryLanguage = null,
        secondaryLanguage = Language(1,"Russian"),
        {},{},{},{}
    )
}

@Preview
@Composable
fun SelectImageStatePrevWhite() = WithLocalProviderForPreview {

    SelectImageState(
        selectedImageUrl = null,
        {},{},{},false
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SelectImageStatePrevDark() = WithLocalProviderForPreview {

    SelectImageState(
        selectedImageUrl = null,
        {},{},{},false
    )
}