package com.xxmrk888ytxx.addwordscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.xxmrk888ytxx.addwordscreen.models.ScreenState
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.WithUiEventHandler

@Composable
fun AddWordScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) = WithUiEventHandler(onEvent = onEvent) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddings ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddings),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
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

            Text(
                text = stringResource(R.string.phrases_with_this_word),
                style = MaterialTheme.typography.titleLarge
            )

            OutlinedButton(
                onClick = { onEvent(LocalUiEvent.AddNewPhraseEvent) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 5.dp).size(24.dp)
                )

                Text(
                    text = stringResource(R.string.add_new_phrase),
                    style = MaterialTheme.typography.bodyLarge
                )
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