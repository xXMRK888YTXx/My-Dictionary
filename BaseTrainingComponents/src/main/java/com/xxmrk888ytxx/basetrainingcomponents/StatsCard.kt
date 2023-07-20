package com.xxmrk888ytxx.basetrainingcomponents

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingProgress

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