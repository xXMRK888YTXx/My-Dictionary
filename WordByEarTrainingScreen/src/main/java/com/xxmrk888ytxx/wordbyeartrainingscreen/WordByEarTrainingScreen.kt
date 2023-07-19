package com.xxmrk888ytxx.wordbyeartrainingscreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.basetrainingcomponents.ConfigurationScreen
import com.xxmrk888ytxx.basetrainingcomponents.LoadingScreen
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.BackNavigationButton
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.wordbyeartrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordbyeartrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordbyeartrainingscreen.models.ScreenType

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WordByEarTrainingScreen(
    screenState:ScreenState,
    onEvent:(UiEvent) -> Unit
) {
    val navigator = LocalNavigator.current

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                screenType = screenState.screenType,
                onBack = { onEvent(LocalUiEvent.BackScreenEvent(navigator)) }
            )
        },
        bottomBar = {
            BottomBar(
                screenType = screenState.screenType,
                isGroupSelected = screenState.availableWordGroup.isNotEmpty(),
                trainingParams = screenState.trainingParams,
                onStartTraining = { onEvent(LocalUiEvent.StartTrainingEvent) }
            )
        }
    ) { paddings ->
        AnimatedContent(
            targetState = screenState.screenType,
            modifier = Modifier
                .padding(10.dp)
                .padding(paddings)
        ) { screenType ->
            when(screenType) {
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
                ScreenType.TRAINING -> {}
                ScreenType.RESULTS -> {}
                ScreenType.LOADING -> LoadingScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    screenType: ScreenType,
    isGroupSelected:Boolean,
    trainingParams: TrainingParams,
    onStartTraining:() -> Unit
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
        ScreenType.TRAINING -> {}
        ScreenType.RESULTS -> {}
        ScreenType.LOADING -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    screenType: ScreenType,
    onBack:() -> Unit
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
            }

        },
        navigationIcon = {
            BackNavigationButton(onClick = onBack)
        }
    )
}
