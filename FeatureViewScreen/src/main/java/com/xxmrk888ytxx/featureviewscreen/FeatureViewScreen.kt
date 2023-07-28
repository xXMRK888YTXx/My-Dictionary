@file:OptIn(ExperimentalFoundationApi::class)

package com.xxmrk888ytxx.featureviewscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalApplicationName
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.featureviewscreen.models.LocalUiEvent
import com.xxmrk888ytxx.featureviewscreen.models.ScreenState
import com.xxmrk888ytxx.featureviewscreen.models.ScreenType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeatureViewScreen(
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {

    val pager = rememberPagerState()

    val pageCount = remember {
        ScreenType.values().size
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

            if (screenState.currentScreenType != ScreenType.AGREE_WITH_RULES) {
                TopBar(
                    onSkip = { onEvent(LocalUiEvent.SkipEvent(pager, scope)) }
                )
            }
        },
        bottomBar = {
            BottomBar(pager, screenState, onEvent)
        }
    ) { paddings ->
        HorizontalPager(
            pageCount = pageCount,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
            userScrollEnabled = false,
            state = pager
        ) {
            when (screenState.currentScreenType) {
                ScreenType.WELCOME -> WelcomeScreenType()
                ScreenType.WORD -> WordScreenType()
                ScreenType.TRAINING -> TrainingScreenType()
                ScreenType.FREE -> FreeScreenType()
                ScreenType.AGREE_WITH_RULES -> AgreeWithRulesScreenType(
                    isAgreeWithPrivacyPolicy = screenState.isAgreeWithPrivacyPolicy,
                    isAgreeWithTermsOfUse = screenState.isAgreeWithTermsOfUse,
                    onAgreeWithPrivacyPolicyStateChanged = {
                        onEvent(
                            LocalUiEvent.ChangeAgreeWithPrivacyPolicyState(
                                it
                            )
                        )
                    },
                    onAgreeWithTermsOfUseStateChanged = {
                        onEvent(
                            LocalUiEvent.ChangeAgreeWithTermsOfUseState(
                                it
                            )
                        )
                    },
                    onOpenPrivacyPolicy = { onEvent(LocalUiEvent.OpenPrivacyPolicy) },
                    onOpenTermsOfUse = { onEvent(LocalUiEvent.OpenTermsOfUse) }
                )
            }
        }
    }
}

@Composable
fun TopBar(
    onSkip: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        TextButton(
            onClick = onSkip,
        ) {
            Text(text = "Skip")
        }
    }
}

@Composable
fun AgreeWithRulesScreenType(
    isAgreeWithPrivacyPolicy: Boolean,
    isAgreeWithTermsOfUse: Boolean,
    onAgreeWithPrivacyPolicyStateChanged: (Boolean) -> Unit,
    onAgreeWithTermsOfUseStateChanged: (Boolean) -> Unit,
    onOpenPrivacyPolicy: () -> Unit,
    onOpenTermsOfUse: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.contract))

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = true,
        speed = 0.8f,
        iterations = 5,
    )

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.weight(1f))


        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "By using the application you will agree with:",
                style = MaterialTheme.typography.bodyLarge
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Switch(
                    checked = isAgreeWithPrivacyPolicy,
                    onCheckedChange = onAgreeWithPrivacyPolicyStateChanged
                )

                TextButton(onClick = onOpenPrivacyPolicy) {
                    Text(
                        text = "Privacy policy",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Switch(
                    checked = isAgreeWithTermsOfUse,
                    onCheckedChange = onAgreeWithTermsOfUseStateChanged
                )

                TextButton(onClick = onOpenTermsOfUse) {
                    Text(
                        text = "Terms of use",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun FreeScreenType() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.free))

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = true,
        speed = 0.8f,
        iterations = 5,
    )

    BaseScreenType(
        icon = {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
        },
        primaryText = "It is free",
        secondaryText = "All features of the application are completely free, except for disabling the display of ads."
    )
}

@Composable
fun TrainingScreenType() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.training))

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = true,
        speed = 0.8f,
        iterations = 5,
    )

    BaseScreenType(
        icon = {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            )
        },
        primaryText = "Train your words",
        secondaryText = "You can train the words that you have written down to consolidate your result or learn new words."
    )
}

@Composable
fun WordScreenType() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.book))

    val progress by animateLottieCompositionAsState(
        composition,
        restartOnPlay = true,
        speed = 0.8f,
        iterations = 5,
    )

    BaseScreenType(
        icon = {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
        },
        primaryText = "Save the translation of words",
        secondaryText = "Write down the translations of the words that you have learned so as not to lose or forget the translation."
    )

}

@Composable
fun BottomBar(
    pagerState: PagerState,
    screenState: ScreenState,
    onEvent: (UiEvent) -> Unit,
) {
    val screenTypeCount = remember {
        ScreenType.values().size
    }

    val scope = rememberCoroutineScope()

    val navigator = LocalNavigator.current

    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        CurrentProgressWidget(screenTypeCount, pagerState.currentPage)

        Button(
            onClick = {
                when (screenState.currentScreenType) {
                    ScreenType.WELCOME -> onEvent(
                        LocalUiEvent.ToWordScreenStateEvent(
                            pagerState,
                            scope
                        )
                    )

                    ScreenType.WORD -> onEvent(
                        LocalUiEvent.ToTrainingScreenStateEvent(
                            pagerState,
                            scope
                        )
                    )

                    ScreenType.TRAINING -> onEvent(
                        LocalUiEvent.ToFreeScreenStateEvent(
                            pagerState,
                            scope
                        )
                    )

                    ScreenType.FREE -> onEvent(
                        LocalUiEvent.ToAgreeWithRulesScreenStateEvent(
                            pagerState,
                            scope
                        )
                    )

                    ScreenType.AGREE_WITH_RULES -> onEvent(LocalUiEvent.ToMainScreenEvent(navigator))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = screenState.currentScreenType != ScreenType.AGREE_WITH_RULES || (screenState.isAgreeWithTermsOfUse && screenState.isAgreeWithPrivacyPolicy)
        ) {
            Text(
                text = if (screenState.currentScreenType != ScreenType.AGREE_WITH_RULES) "Next" else "To application"
            )
        }
    }
}

@Composable
fun CurrentProgressWidget(
    screenTypeCount: Int,
    currentPage: Int,
) {

    val circleColor = MaterialTheme.colorScheme.primary

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(screenTypeCount) {
            Canvas(modifier = Modifier.size(8.dp), onDraw = {
                drawCircle(
                    color = circleColor,
                    alpha = if (currentPage == it) 1f else 0.5f
                )
            })
        }
    }
}

@Composable
fun WelcomeScreenType() {
    BaseScreenType(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_android_24),
                contentDescription = "",
                modifier = Modifier.size(200.dp)
            )
        },
        primaryText = "Welcome in " + LocalApplicationName.current + " application",
        secondaryText = "Let's take a little tour of the app's capabilities"
    )
}


@Composable
private fun BaseScreenType(
    icon: @Composable () -> Unit,
    primaryText: String,
    secondaryText: String,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        icon()

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Text(
            text = primaryText,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = secondaryText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}