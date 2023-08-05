package com.xxmrk888ytxx.wordtranslatetrainingscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.xxmrk888ytxx.basetrainingcomponents.ConfigurationScreen
import com.xxmrk888ytxx.basetrainingcomponents.ExitDialog
import com.xxmrk888ytxx.basetrainingcomponents.LoadingScreen
import com.xxmrk888ytxx.basetrainingcomponents.ResultScreen
import com.xxmrk888ytxx.basetrainingcomponents.models.CheckResultState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalAdController
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenType

/**
 * [Ru]
 * Экран для тренировки перевода слов
 */

/**
 * [En]
 * Screen for training translation of words
 */
@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun WordTranslateTrainingScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val navigator = LocalNavigator.current

    val pager = rememberPagerState()

    val scope = rememberCoroutineScope()

    val adController = LocalAdController.current

    BackHandler(
        enabled = screenState.screenType == ScreenType.TRAINING
    ) {
        onEvent(LocalUiEvent.ShowExitDialog)
    }

    Scaffold(
        Modifier
            .fillMaxSize(),
        bottomBar = {
            Column {
                when (screenState.screenType) {
                    ScreenType.CONFIGURATION -> {
                        Button(
                            onClick = { onEvent(LocalUiEvent.StartTrainingEvent) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            enabled = screenState.availableWordGroup.isNotEmpty()
                                    && screenState.trainingParams.selectedWordGroupsId.isNotEmpty()
                                    && screenState.trainingParams.questionCount != Int.MIN_VALUE
                        ) {
                            Text(text = stringResource(R.string.start))
                        }
                    }

                    ScreenType.TRAINING -> {
                        Button(
                            onClick = {
                                if(screenState.trainingProgress.checkResultState !is CheckResultState.None) {
                                    onEvent(LocalUiEvent.NextQuestion(pager, scope))
                                } else {
                                    onEvent(LocalUiEvent.CheckQuestion)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            enabled = screenState.trainingProgress.currentAnswer.isNotEmpty()
                        ) {
                            if(screenState.trainingProgress.checkResultState is CheckResultState.None) {
                                Text(text = stringResource(R.string.check_the_answer))
                            } else {
                                Text(text = stringResource(R.string.next))
                            }
                        }
                    }

                    ScreenType.RESULTS -> {
                        Button(
                            onClick = { onEvent(LocalUiEvent.BackScreenEvent(navigator)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                        ) {
                            Text(text = stringResource(R.string.exit))
                        }
                    }
                    ScreenType.LOADING -> {}
                }

                adController.TrainingBanner()
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier,
                title = {

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = when (screenState.screenType) {
                                ScreenType.CONFIGURATION -> stringResource(R.string.configuration)
                                ScreenType.TRAINING -> stringResource(R.string.training)
                                ScreenType.RESULTS -> stringResource(R.string.results)
                                ScreenType.LOADING -> stringResource(R.string.loading)
                            }
                        )

                        when (screenState.screenType) {
                            ScreenType.TRAINING -> {
                                Text(
                                    text = "${screenState.trainingProgress.currentPage} / ${screenState.question.size}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                            else -> {}
                        }
                    }

                },
                navigationIcon = {
                    BackNavigationButton {
                        if(screenState.screenType == ScreenType.TRAINING) {
                            onEvent(LocalUiEvent.ShowExitDialog)
                        } else {
                            onEvent(LocalUiEvent.BackScreenEvent(navigator))
                        }
                    }
                }
            )
        }
    ) { paddings ->
        AnimatedContent(
            targetState = screenState.screenType,
            modifier = Modifier
                .padding(10.dp)
                .padding(paddings), label = ""
        ) { screenType ->
            when (screenType) {
                ScreenType.CONFIGURATION -> {
                    ConfigurationScreen(
                        trainingParams = screenState.trainingParams,
                        wordGroups = screenState.availableWordGroup,
                        onNumberOfQuestionsChanged = {
                            onEvent(LocalUiEvent.NumberOfQuestionsChangedEvent(it))
                        },
                        onChangeIsUsePhrases = {
                            onEvent(LocalUiEvent.ChangeIsUsePhrasesEvent(it))
                        },
                        onIsGroupWord = {
                            screenState.trainingParams.selectedWordGroupsId.contains(it)
                        },
                        onChangeWordGroupSelectedState = {
                            onEvent(LocalUiEvent.ChangeWordGroupSelectedStateEvent(it))
                        }
                    )
                }

                ScreenType.TRAINING -> {
                    TrainingScreenType(
                        pager = pager,
                        questionCount = screenState.question.size,
                        answerText = screenState.trainingProgress.currentAnswer,
                        onChangeAnswerText = {
                            onEvent(LocalUiEvent.ChangeAnswerTextEvent(it))
                        },
                        onGetCurrentQuestion = {
                            screenState.question[it].word
                        },
                        checkResultState = screenState.trainingProgress.checkResultState
                    )
                }

                ScreenType.RESULTS -> ResultScreen(screenState.trainingProgress)

                ScreenType.LOADING -> LoadingScreen()
            }
        }
    }

    if(screenState.isExitDialogVisible) {
        ExitDialog(
            onDismiss = {
                onEvent(LocalUiEvent.HideExitDialog)
            },
            onExit = {
                onEvent(LocalUiEvent.HideExitDialog)
                onEvent(LocalUiEvent.BackScreenEvent(navigator))
            }
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TrainingScreenType(
    pager: PagerState,
    questionCount: Int,
    answerText: String,
    onChangeAnswerText: (String) -> Unit,
    onGetCurrentQuestion: (Int) -> String,
    checkResultState: CheckResultState
) {

    HorizontalPager(
        pageCount = questionCount,
        userScrollEnabled = false,
        state = pager
    ) { currentPage ->

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {

            AnimatedVisibility(
                visible = checkResultState !is CheckResultState.None,
                enter = slideInHorizontally(
                    initialOffsetX = { -it }
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { 0 },
                )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            Icon(
                                painter = when (checkResultState) {
                                    is CheckResultState.Failed -> painterResource(id = R.drawable.baseline_clear_24)

                                    else -> painterResource(
                                        id = R.drawable.baseline_done_24
                                    )
                                },
                                contentDescription = "",
                                tint = when (checkResultState) {
                                    is CheckResultState.Failed -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )

                            Text(
                                text = if(checkResultState is CheckResultState.Failed)
                                    stringResource(R.string.unfortunately_this_is_the_wrong_answer)
                                else stringResource(R.string.you_are_right)
                            )
                        }

                        Text(
                            text = if(checkResultState is CheckResultState.Failed)
                                stringResource(R.string.correct_answer_was) + "\"${checkResultState.correctAnswer}\""
                            else stringResource(R.string.congratulations)
                        )


                    }
                }
            }

            Text(
                text = stringResource(R.string.enter_the_translate_of_the_word),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = onGetCurrentQuestion(currentPage),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = answerText,
                onValueChange = onChangeAnswerText,
                singleLine = true,
                label = {
                    Text(
                        text = stringResource(R.string.answer),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(0.9f),
                enabled = checkResultState is CheckResultState.None
            )

        }
    }
}
