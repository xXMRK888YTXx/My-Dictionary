package com.xxmrk888ytxx.wordbyeartrainingscreen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.basetrainingcomponents.ConfigurationScreen
import com.xxmrk888ytxx.basetrainingcomponents.ExitDialog
import com.xxmrk888ytxx.basetrainingcomponents.LoadingScreen
import com.xxmrk888ytxx.basetrainingcomponents.ResultScreen
import com.xxmrk888ytxx.basetrainingcomponents.models.CheckResultState
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingProgress
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalAdController
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.wordbyeartrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordbyeartrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordbyeartrainingscreen.models.ScreenType


/**
 * [Ru]
 *Экран для тренировки слов на слух
 */

/**
 * [En]
 * Screen for training words by ear
 */
@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun WordByEarTrainingScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {
    val navigator = LocalNavigator.current

    val pager = rememberPagerState { screenState.questions.size }

    val scope = rememberCoroutineScope()

    val adController = LocalAdController.current

    BackHandler(
        enabled = screenState.screenType == ScreenType.TRAINING
    ) {
        onEvent(LocalUiEvent.ShowExitDialog)
    }

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                screenType = screenState.screenType,
                onBack = {
                    if (screenState.screenType == ScreenType.TRAINING) {
                        onEvent(LocalUiEvent.ShowExitDialog)
                    } else {
                        onEvent(LocalUiEvent.BackScreenEvent(navigator))
                    }
                },
                currentQuestion = screenState.trainingProgress.currentPage,
                questionCount = screenState.questions.size
            )
        },
        bottomBar = {

            Column {
                BottomBar(
                    screenType = screenState.screenType,
                    trainingProgress = screenState.trainingProgress,
                    isGroupSelected = screenState.availableWordGroup.isNotEmpty(),
                    trainingParams = screenState.trainingParams,
                    onStartTraining = { onEvent(LocalUiEvent.StartTrainingEvent) },
                    onCheckAnswer = { onEvent(LocalUiEvent.CheckAnswer) },
                    onNextQuestion = { onEvent(LocalUiEvent.NextQuestion(pager, scope)) },
                    onBackScreen = { onEvent(LocalUiEvent.BackScreenEvent(navigator)) }
                )

                adController.TrainingBanner()
            }
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
                        answerText = screenState.trainingProgress.currentAnswer,
                        onChangeAnswerText = {
                            onEvent(LocalUiEvent.ChangeAnswerTextEvent(it))
                        },
                        onPlayCurrentQuestion = {
                            onEvent(LocalUiEvent.PlayQuestionWordEvent(it))
                        },
                        checkResultState = screenState.trainingProgress.checkResultState
                    )
                }

                ScreenType.RESULTS -> {
                    ResultScreen(trainingProgress = screenState.trainingProgress)
                }

                ScreenType.LOADING -> LoadingScreen()
            }
        }
    }

    if (screenState.isExitDialogVisible) {
        ExitDialog(
            onDismiss = { onEvent(LocalUiEvent.HideExitDialog) },
            onExit = {
                onEvent(LocalUiEvent.HideExitDialog)
                onEvent(LocalUiEvent.BackScreenEvent(navigator))
            }
        )
    }
}

@Composable
fun BottomBar(
    screenType: ScreenType,
    isGroupSelected: Boolean,
    trainingParams: TrainingParams,
    trainingProgress: TrainingProgress,
    onStartTraining: () -> Unit,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onBackScreen: () -> Unit,
) {
    when (screenType) {
        ScreenType.CONFIGURATION -> {
            Button(
                onClick = onStartTraining,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                enabled = isGroupSelected
                        && trainingParams.selectedWordGroupsId.isNotEmpty()
                        && trainingParams.questionCount != Int.MIN_VALUE
            ) {
                Text(text = stringResource(R.string.start))
            }
        }

        ScreenType.TRAINING -> {
            Button(
                onClick = {
                    if (trainingProgress.checkResultState !is CheckResultState.None) {
                        onNextQuestion()
                    } else {
                        onCheckAnswer()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                enabled = trainingProgress.currentAnswer.isNotEmpty()
            ) {
                if (trainingProgress.checkResultState is CheckResultState.None) {
                    Text(text = stringResource(R.string.check_the_answer))
                } else {
                    Text(text = stringResource(R.string.next))
                }
            }
        }

        ScreenType.RESULTS -> {
            Button(
                onClick = onBackScreen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Text(text = stringResource(R.string.exit))
            }
        }

        ScreenType.LOADING -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    screenType: ScreenType,
    onBack: () -> Unit,
    currentQuestion: Int,
    questionCount: Int,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier,
        title = {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = when (screenType) {
                        ScreenType.CONFIGURATION -> stringResource(R.string.configuration)
                        ScreenType.TRAINING -> stringResource(R.string.training)
                        ScreenType.RESULTS -> stringResource(R.string.results)
                        ScreenType.LOADING -> stringResource(R.string.loading)
                    }
                )

                when (screenType) {
                    ScreenType.TRAINING -> {
                        Text(
                            text = "$currentQuestion / $questionCount",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    else -> {}
                }
            }

        },
        navigationIcon = {
            BackNavigationButton(onClick = onBack)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TrainingScreenType(
    pager: PagerState,
    answerText: String,
    onChangeAnswerText: (String) -> Unit,
    onPlayCurrentQuestion: (Int) -> Unit,
    checkResultState: CheckResultState,
) {

    HorizontalPager(
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
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
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
                                text = if (checkResultState is CheckResultState.Failed)
                                    stringResource(R.string.unfortunately_this_is_the_wrong_answer)
                                else stringResource(R.string.you_are_right)
                            )
                        }

                        Text(
                            text = if (checkResultState is CheckResultState.Failed)
                                stringResource(R.string.correct_answer_was) + "\"${checkResultState.correctAnswer}\""
                            else stringResource(R.string.congratulations)
                        )


                    }
                }
            }

            Text(
                text = stringResource(R.string.write_the_spoken_word),
                style = MaterialTheme.typography.titleMedium
            )


            Button(onClick = { onPlayCurrentQuestion(currentPage) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_volume_up_24),
                    contentDescription = "",
                    modifier = Modifier.size(72.dp)
                )
            }


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
