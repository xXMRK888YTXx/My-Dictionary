package com.xxmrk888ytxx.addwordscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.addwordscreen.models.LocalUiEvent
import com.xxmrk888ytxx.addwordscreen.models.PhrasesModel
import com.xxmrk888ytxx.addwordscreen.models.ScreenState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.corecompose.theme.ui.theme.WithUiEventHandler
import kotlinx.collections.immutable.ImmutableList

@Composable
fun AddWordScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) = WithUiEventHandler(onEvent = onEvent) {

    val navigator = LocalNavigator.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {

            if(
                screenState.enteredWordTextFieldText.isNotEmpty()
                && screenState.translateForEnteredWordTextFieldText.isNotEmpty()
            ) {
                FloatingActionButton(
                    onClick = {
                        onEvent(LocalUiEvent.WordInfoEnterCompleted(navigator))
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_done_24
                        ),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) { paddings ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddings),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        LocalConfigurationTextField(
                            text = screenState.enteredWordTextFieldText,
                            title = stringResource(R.string.enter_the_word_you_want_to_add),
                            label = stringResource(R.string.word),
                            onChangeValue = { onEvent(LocalUiEvent.EnteredWordTextFieldInputEvent(it)) })


                        LocalConfigurationTextField(
                            text = screenState.translateForEnteredWordTextFieldText,
                            title = stringResource(R.string.enter_a_translate_for_this_word),
                            label = stringResource(R.string.translate_of_word),
                            onChangeValue = {
                                onEvent(
                                    LocalUiEvent.TranslateForEnteredWordTextFieldInputEvent(
                                        it
                                    )
                                )
                            }
                        )

                        LocalConfigurationTextField(
                            text = screenState.transcriptTextFieldText,
                            title = stringResource(R.string.enter_a_transcription_for_this_word),
                            label = stringResource(R.string.transcription_of_word),
                            onChangeValue = { onEvent(LocalUiEvent.TranscriptTextFieldInputEvent(it)) }
                        )
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.phrases_with_this_word),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                OutlinedButton(
                    onClick = { onEvent(LocalUiEvent.AddNewPhraseEvent) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .size(24.dp)
                    )

                    Text(
                        text = stringResource(R.string.add_new_phrase),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            phrasesList(
                screenState.phrasesList,
                onChangeTextForOriginalPhrase = { localId, text ->
                    onEvent(LocalUiEvent.UpdateOriginalPhraseEvent(localId, text))
                },
                onChangeTextForTranslatePhrase = { localId, text ->
                    onEvent(LocalUiEvent.UpdateTranslatePhraseEvent(localId, text))
                },
                onRemovePhrase = {
                    onEvent(LocalUiEvent.RemovePhraseEvent(it))
                }
            )

        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.phrasesList(
    phrases: ImmutableList<PhrasesModel>,
    onChangeTextForOriginalPhrase: (Int, String) -> Unit,
    onChangeTextForTranslatePhrase: (Int, String) -> Unit,
    onRemovePhrase: (Int) -> Unit,
) {
    items(phrases, key = { it.localId }) { model ->
        Card(
            modifier = Modifier
                .animateItemPlacement()
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LocalConfigurationTextField(
                    text = model.phrasesText,
                    title = stringResource(R.string.enter_original_phrase),
                    label = stringResource(R.string.original_phrase),
                    onChangeValue = { onChangeTextForOriginalPhrase(model.localId, it) }
                )

                LocalConfigurationTextField(
                    text = model.phrasesTranslate,
                    title = stringResource(R.string.enter_translate_phrase),
                    label = stringResource(R.string.translate_phrase),
                    onChangeValue = { onChangeTextForTranslatePhrase(model.localId, it) }
                )

                Button(onClick = { onRemovePhrase(model.localId) }) {
                    Text(text = stringResource(R.string.remove))
                }

            }
        }
    }
}

@Composable
private fun LocalConfigurationTextField(
    text: String,
    title: String,
    label: String,
    onChangeValue: (String) -> Unit,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )

    OutlinedTextField(
        value = text,
        onValueChange = onChangeValue,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label, style = MaterialTheme.typography.bodyLarge) },
        singleLine = true
    )

}