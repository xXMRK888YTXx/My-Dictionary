package com.xxmrk888ytxx.basetrainingcomponents

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.sp

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