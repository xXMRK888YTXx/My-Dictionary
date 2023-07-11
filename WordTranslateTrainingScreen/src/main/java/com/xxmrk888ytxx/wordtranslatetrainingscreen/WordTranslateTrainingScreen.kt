package com.xxmrk888ytxx.wordtranslatetrainingscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenType
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingParams
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.WordGroup
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WordTranslateTrainingScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        bottomBar = {
            when(screenState.screenType) {
                ScreenType.CONFIGURATION -> {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        enabled = screenState.availableWordGroup.isNotEmpty()
                    ) {
                        Text(text = "Start")
                    }
                }
                ScreenType.TRAINING -> {}
                ScreenType.RESULTS -> {}
            }
        }
    ) { paddings ->
        AnimatedContent(
            targetState = screenState.screenType,
            modifier = Modifier.padding(paddings)
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
                        }
                    )
                }

                ScreenType.TRAINING -> TODO()
                ScreenType.RESULTS -> TODO()
            }
        }
    }
}

@Composable
private fun ConfigurationScreenType(
    trainingParams: TrainingParams,
    wordGroups: ImmutableList<WordGroup>,
    onNumberOfQuestionsChanged: (String) -> Unit,
    onChangeIsUsePhrases: (Boolean) -> Unit,
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
                verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = "Select the word groups that will be used.",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "It's allowed to use groups of words containing more than 10 words.",
                    style = MaterialTheme.typography.bodyMedium
                )

                if(wordGroups.isEmpty()) {
                    Text(text = "You don't have word groups")
                } else {
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)) {
                        items(wordGroups) {
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(text = it.name,style = MaterialTheme.typography.titleMedium)

                                Spacer(modifier = Modifier.weight(1f))

                                Switch(
                                    checked = false,
                                    onCheckedChange = {}
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