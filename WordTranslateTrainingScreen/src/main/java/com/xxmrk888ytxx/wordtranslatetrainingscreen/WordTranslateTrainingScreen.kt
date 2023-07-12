package com.xxmrk888ytxx.wordtranslatetrainingscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenType
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingParams
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.WordGroup
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WordTranslateTrainingScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val navigator = LocalNavigator.current

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
                        enabled = screenState.availableWordGroup.isNotEmpty() && screenState.trainingParams.selectedWordGroupsId.isNotEmpty()
                    ) {
                        Text(text = "Start")
                    }
                }

                ScreenType.TRAINING -> {
                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        enabled = true
                    ) {
                        Text(text = "Next")
                    }
                }

                ScreenType.RESULTS -> {}
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
                                ScreenType.CONFIGURATION -> "Configuration"
                                ScreenType.TRAINING -> "Training"
                                ScreenType.RESULTS -> "Results"
                                ScreenType.LOADING -> "Loading"
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
                        onEvent(LocalUiEvent.BackScreenEvent(navigator))
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
                    ConfigurationScreenType(
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
                        questionCount = screenState.question.size,
                        answerText = screenState.trainingProgress.currentAnswer,
                        onChangeAnswerText = {
                            onEvent(LocalUiEvent.ChangeAnswerTextEvent(it))
                        },
                        onGetCurrentQuestion = {
                            screenState.question[it].word
                        }
                    )
                }

                ScreenType.RESULTS -> {}
                ScreenType.LOADING -> LoadingScreenType()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TrainingScreenType(
    questionCount: Int,
    answerText: String,
    onChangeAnswerText: (String) -> Unit,
    onGetCurrentQuestion: (Int) -> String,
) {


    HorizontalPager(
        pageCount = questionCount,
        userScrollEnabled = false
    ) { currentPage ->

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Enter the translate of the word:",
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
                        text = "Answer",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(0.9f)
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
        Text(text = "Wait,loading")

        LinearProgressIndicator()
    }
}

@Composable
private fun ConfigurationScreenType(
    trainingParams: TrainingParams,
    wordGroups: ImmutableList<WordGroup>,
    onNumberOfQuestionsChanged: (String) -> Unit,
    onChangeIsUsePhrases: (Boolean) -> Unit,
    onIsGroupWord: (Int) -> Boolean,
    onChangeWordGroupSelectedState: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        paramContainer {
            Text(text = "Number of questions")

            Spacer(modifier = Modifier.weight(1f))

            TextField(
                value = trainingParams.questionCount.toString(),
                onValueChange = onNumberOfQuestionsChanged,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(60.dp)
            )
        }

        paramContainer {
            Text(text = "Need use phrases")

            Spacer(modifier = Modifier.weight(1f))

            Switch(
                checked = trainingParams.isUsePhrases,
                onCheckedChange = onChangeIsUsePhrases
            )
        }

        paramContainer(placeInCenter = true) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = "Select the word groups that will be used.",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "It's allowed to use groups of words containing more than 5 words.",
                    style = MaterialTheme.typography.bodyMedium
                )

                if (wordGroups.isEmpty()) {
                    Text(text = "You don't have word groups")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        items(wordGroups) {
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(text = it.name, style = MaterialTheme.typography.titleMedium)

                                Spacer(modifier = Modifier.weight(1f))

                                Switch(
                                    checked = onIsGroupWord(it.wordGroupId),
                                    onCheckedChange = { _ ->
                                        onChangeWordGroupSelectedState(it.wordGroupId)
                                    }
                                )
                            }
                        }
                    }
                }


            }
        }
    }
}

private fun LazyListScope.paramContainer(
    placeInCenter: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) {
    item {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                10.dp,
                if (placeInCenter) Alignment.CenterHorizontally else Alignment.Start
            ),
            content = content
        )
    }
}