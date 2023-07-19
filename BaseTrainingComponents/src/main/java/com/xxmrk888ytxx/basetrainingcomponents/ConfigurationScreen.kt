package com.xxmrk888ytxx.basetrainingcomponents

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingParams
import com.xxmrk888ytxx.basetrainingcomponents.models.WordGroup
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ConfigurationScreen(
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
            Text(text = stringResource(R.string.number_of_questions))

            Spacer(modifier = Modifier.weight(1f))

            TextField(
                value = if(trainingParams.questionCount != Int.MIN_VALUE) trainingParams.questionCount.toString()
                else "",
                onValueChange = onNumberOfQuestionsChanged,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(60.dp)
            )
        }

        paramContainer {
            Text(text = stringResource(R.string.need_use_phrases))

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
                    text = stringResource(R.string.select_the_word_groups_that_will_be_used),
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(R.string.it_s_allowed_to_use_groups_of_words_containing_more_than_5_words),
                    style = MaterialTheme.typography.bodyMedium
                )

                if (wordGroups.isEmpty()) {
                    Text(text = stringResource(R.string.you_don_t_have_word_groups))
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