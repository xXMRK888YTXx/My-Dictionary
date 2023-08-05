package com.xxmrk888ytxx.basetrainingcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.xxmrk888ytxx.basetrainingcomponents.models.TrainingProgress

@Composable
fun ResultScreen(trainingProgress: TrainingProgress) {
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