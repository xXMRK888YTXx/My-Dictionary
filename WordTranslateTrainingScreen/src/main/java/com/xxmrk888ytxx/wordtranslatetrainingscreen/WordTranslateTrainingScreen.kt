package com.xxmrk888ytxx.wordtranslatetrainingscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.xxmrk888ytxx.basetrainingcomponents.ConfigurationScreen
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.CheckResultState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenType
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingProgress

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

    BackHandler(
        enabled = screenState.screenType == ScreenType.TRAINING
    ) {
        onEvent(LocalUiEvent.ShowExitDialog)
    }

    Scaffold(
        Modifier
            .fillMaxSize(),
        bottomBar = {
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
                .padding(paddings)
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

                ScreenType.RESULTS -> ResultScreenType(screenState.trainingProgress)

                ScreenType.LOADING -> LoadingScreenType()
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

@Composable
fun ExitDialog(onDismiss: () -> Unit, onExit: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.leave_from_training),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = stringResource(R.string.the_progress_won_t_be_saved),
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.cancel))
                    }

                    TextButton(onClick = onExit) {
                        Text(text = stringResource(R.string.exit))
                    }
                }
            }
        }
    }
}

@Composable
fun ResultScreenType(trainingProgress: TrainingProgress) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            StatsCard(trainingProgress)
        }

        item {

            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    when {
                        trainingProgress.correctAnswers >= trainingProgress.incorrectAnswers -> R.raw.congratulation_anim

                        else -> R.raw.failure_anim
                    }
                )
            )

            val progress by animateLottieCompositionAsState(
                composition,
                restartOnPlay = true,
                speed = 0.6f,
                iterations = 5,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 25.dp)
            ) {
                Text(
                    text = when {
                        trainingProgress.correctAnswers >= trainingProgress.incorrectAnswers -> stringResource(R.string.keep_up_the_good_work)

                        else -> stringResource(R.string.don_t_be_upset_try_again)
                    },
                    style = MaterialTheme.typography.titleLarge
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                )
            }
        }
    }
}

@Composable
fun StatsCard(trainingProgress: TrainingProgress) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(26.dp)
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    10.dp,
                    Alignment.CenterHorizontally
                )
            ) {

                Icon(
                    painter = when {
                        trainingProgress.correctAnswers >= trainingProgress.incorrectAnswers -> painterResource(
                            id = R.drawable.baseline_done_24
                        )

                        else -> painterResource(id = R.drawable.baseline_clear_24)
                    },
                    contentDescription = "",
                    tint = when {
                        trainingProgress.correctAnswers >= trainingProgress.incorrectAnswers -> MaterialTheme.colorScheme.primary

                        else -> MaterialTheme.colorScheme.error
                    }
                )


                Text(
                    text = when {
                        trainingProgress.incorrectAnswers == 0 -> stringResource(R.string.congratulation)

                        trainingProgress.correctAnswers >= trainingProgress.incorrectAnswers -> stringResource(R.string.good_but_you_can_do_better)

                        else -> stringResource(R.string.try_again)
                    },
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatsCircle(
                    modifier = Modifier.size(140.dp),
                    trainingProgress.correctAnswers,
                    trainingProgress.incorrectAnswers
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val primaryColor = MaterialTheme.colorScheme.primary

                    val errorColor = MaterialTheme.colorScheme.error

                    val circleSize = remember {
                        10.dp
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Canvas(
                            modifier = Modifier.size(circleSize),
                            onDraw = {
                                drawCircle(primaryColor)
                            })

                        Text(text = stringResource(R.string.correct) + " ${trainingProgress.correctAnswers}")
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Canvas(
                            modifier = Modifier.size(circleSize),
                            onDraw = {
                                drawCircle(errorColor)
                            })

                        Text(text = stringResource(R.string.incorrect) + " ${trainingProgress.incorrectAnswers}")
                    }

                }
            }
        }
    }
}

@Composable
fun StatsCircle(
    modifier: Modifier,
    correctAnswers: Int,
    incorrectAnswers: Int,
) {
    val totalAnswers = remember(
        key1 = correctAnswers,
        key2 = incorrectAnswers
    ) {
        correctAnswers + incorrectAnswers
    }

    val correctAnswersPercent = remember(totalAnswers) {
        100f * (correctAnswers.toFloat() / totalAnswers.toFloat())
    }


    val animateCorrectAnswers = animateFloatAsState(
        targetValue = correctAnswers.toFloat(),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy
        )
    )

    val incorrectColor = MaterialTheme.colorScheme.error

    val correctColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .then(modifier)
            .drawBehind {
                this.drawArc(
                    useCenter = false,
                    color = incorrectColor,
                    startAngle = 360f,
                    sweepAngle = 360f,
                    style = Stroke(
                        cap = StrokeCap.Round,
                        width = 40f
                    )
                )

                this.drawArc(
                    useCenter = false,
                    color = correctColor,
                    startAngle = 270f,
                    sweepAngle = 360f * (animateCorrectAnswers.value / totalAnswers.toFloat()),
                    style = Stroke(
                        cap = StrokeCap.Round,
                        width = 40f
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${correctAnswersPercent.toInt()} %", fontSize = 30.sp)
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


@Composable
private fun LoadingScreenType() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.wait_loading))

        LinearProgressIndicator()
    }
}

