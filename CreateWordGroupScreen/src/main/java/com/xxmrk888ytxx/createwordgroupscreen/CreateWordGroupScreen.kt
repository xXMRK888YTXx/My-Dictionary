package com.xxmrk888ytxx.createwordgroupscreen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.WithLocalProviderForPreview
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.createwordgroupscreen.models.LocalUiEvent
import com.xxmrk888ytxx.createwordgroupscreen.models.Pages
import com.xxmrk888ytxx.createwordgroupscreen.models.ScreenState
import kotlinx.collections.immutable.ImmutableList

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
                    IconButton(onClick = { }) {
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
                            onSelectPrimaryLanguage = { onEvent(LocalUiEvent.SelectNewPrimaryLanguageEvent(it)) },
                            onSelectSecondaryLanguage = { onEvent(LocalUiEvent.SelectNewSecondaryLanguageEvent(it)) },
                            onNewLanguageRequest = {},
                            onLanguageSelectCompleted = { onEvent(LocalUiEvent.LanguageSelectCompletedEvent(pagerState,scope)) }
                        )
                    }

                    Pages.SELECT_IMAGE -> {

                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLanguagesState(
    availableLanguage: ImmutableList<Language>,
    primaryLanguage: Language?,
    secondaryLanguage: Language?,
    onSelectPrimaryLanguage: (Language) -> Unit,
    onSelectSecondaryLanguage: (Language) -> Unit,
    onNewLanguageRequest: () -> Unit,
    onLanguageSelectCompleted:() -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.translate_language_animation))

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = true,
        speed = 0.6f,
        iterations = 3,
    )

    Scaffold(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        bottomBar = {
            Button(
                onClick = onLanguageSelectCompleted,
                modifier = Modifier
                    .fillMaxWidth()
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
                items(availableLanguage) {
                    FilterChip(
                        selected = it.id == primaryLanguage?.id,
                        onClick = { onSelectPrimaryLanguage(it) },
                        label = { Text(text = it.name) },
                        enabled = it.id != secondaryLanguage?.id,
                        modifier = Modifier.padding(end = 10.dp)
                    )
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
    item {
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
                    .fillMaxWidth()
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
        }
    }
}


@Preview
@Composable
fun EnterGroupNamePagePrevWhite() = WithLocalProviderForPreview {
    val screenState = ScreenState()

    CreateWordGroupScreen(screenState) {}
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun EnterGroupNamePrevDark() = WithLocalProviderForPreview {
    val screenState = ScreenState()

    CreateWordGroupScreen(screenState) {}
}