package com.xxmrk888ytxx.corecompose.theme.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.corecompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewLanguageDialog(
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