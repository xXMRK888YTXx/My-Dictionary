package com.xxmrk888ytxx.translatorscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorScreen(
    screenState: ScreenState,
    onEvent:(UiEvent) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(LocalConfiguration.current.screenHeightDp.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Translator",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TranslateCard(
                text = screenState.textForState,
                onChangeText = { onEvent(LocalUiEvent.TextForTranslateInput(it)) },
                onClear = { onEvent(LocalUiEvent.ClearTextForTranslate) }
            )
        }
    }
}

@Composable
fun ColumnScope.TranslateCard(
    text:String,
    onChangeText:(String) -> Unit,
    onClear:() -> Unit
) {
    val isEmpty = remember(key1 = text) {
        text.isEmpty()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(16.dp)
        ,
        shape = RoundedCornerShape(
            20.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            BasicTextField(
                value = text,
                onValueChange = onChangeText,
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    color = if(isSystemInDarkTheme()) Color(0xFFC2C9BD) else Color(0xFF424940),
                    fontSize = 22.sp
                ),
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 10.dp
                    ),
                decorationBox = {

                    Row(

                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(modifier = Modifier.align(Alignment.Top).weight(1f)) {
                            if(isEmpty) {
                                Text(
                                    text = "Enter the text for translate",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            it()
                        }

                        IconButton(
                            onClick = { onClear() },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            )
        }
    }

    AnimatedVisibility(
        isEmpty,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .weight(1f),
           verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Text(
               text = "Text for translate is empty",
               style = MaterialTheme.typography.titleLarge
           )

           Text(
               text = "Start typing",
               style = MaterialTheme.typography.titleMedium
           )
       }
    }
}