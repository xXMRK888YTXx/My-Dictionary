package com.xxmrk888ytxx.translatorscreen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.IdRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.coreandroid.ActivityContracts.SpeechRecognizeContract.SpeechRecognizeContract
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.translatorscreen.models.LocalUiEvent
import com.xxmrk888ytxx.translatorscreen.models.ScreenState
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorScreen(
    screenState: ScreenState,
    onEvent:(UiEvent) -> Unit
) {
    
    

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val clipboardManager = LocalClipboardManager.current


    val context = LocalContext.current

    val uiScope = rememberCoroutineScope()

    val speechRecognizeContract = rememberLauncherForActivityResult(
        contract = SpeechRecognizeContract(),
        onResult = { recognizedText ->
            recognizedText?.let { onEvent(LocalUiEvent.SpeechRecognizedEvent(it)) }
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(LocalConfiguration.current.screenHeightDp.dp),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.translator),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
                    .padding(
                        top = 10.dp,
                        start = 30.dp,
                        end = 30.dp,
                        bottom = 10.dp
                    )
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "English")
                }
                
                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(
                        id = R.drawable.baseline_compare_arrows_24),
                        contentDescription = ""
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))

                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Russian")
                }
                
            }
            
            TranslateCard(
                text = screenState.textForState,
                onChangeText = { onEvent(LocalUiEvent.TextForTranslateInput(it)) },
                onClear = { onEvent(LocalUiEvent.ClearTextForTranslate) },
                onAskText = { onEvent(LocalUiEvent.AskTestEvent) },
                onPastFromClipboard = { if(clipboardManager.hasText()) onEvent(LocalUiEvent.PastTextFromClipboard(clipboardManager.getText()?.text)) },
                onDetectTextByCamera = {  },
                onRecognizeVoice = { onEvent(LocalUiEvent.RequestRecognizeSpeech(speechRecognizeContract,snackbarHostState,context,uiScope)) }
            )
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun ColumnScope.TranslateCard(
    text:String,
    onChangeText:(String) -> Unit,
    onClear:() -> Unit,
    onAskText:() -> Unit,
    onPastFromClipboard:() -> Unit,
    onDetectTextByCamera:() -> Unit,
    onRecognizeVoice: () -> Unit
) {
    data class Action(
        @IdRes val icon:Int,
        val isEnabled:Boolean = true,
        val onClick:() -> Unit
    )

    val isEmpty = remember(key1 = text) {
        text.isEmpty()
    }

    val textForTranslateActions = remember(isEmpty) {
        persistentListOf(
            Action(
                icon = R.drawable.baseline_close_24,
                onClick = onClear,
                isEnabled = !isEmpty
            ),
            Action(
                icon = R.drawable.baseline_volume_up_24,
                onClick = onAskText,
                isEnabled = !isEmpty
            ),
            Action(
                icon = R.drawable.baseline_content_paste_24,
                onClick = onPastFromClipboard
            ),
            Action(
                icon = R.drawable.baseline_photo_camera_24,
                onClick = onDetectTextByCamera
            ),
            Action(
                icon = R.drawable.baseline_keyboard_voice_24,
                onClick = onRecognizeVoice
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
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
                    .fillMaxSize()
                    .padding(
                        top = 10.dp,
                        start = 10.dp
                    ),
                decorationBox = {
                    Column {
                        Row(
                            modifier = Modifier
                        ) {
                            Box(modifier = Modifier
                                .align(Alignment.Top)
                                .weight(1f)) {
                                if(isEmpty) {
                                    Text(
                                        text = stringResource(R.string.enter_the_text_for_translate),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }

                                it()
                            }

                            LazyColumn(
                                modifier = Modifier
                                    .animateContentSize(),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                items(textForTranslateActions, key = { it.icon }) {
                                    IconButton(
                                        onClick = it.onClick,
                                        enabled = it.isEnabled
                                    ) {
                                        Icon(
                                            painter = painterResource(id = it.icon),
                                            contentDescription = "",
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            )

        }
    }

    AnimatedVisibility(
        true,
        modifier = Modifier
            .fillMaxWidth()
    ) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .weight(1f),
           verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.CenterVertically),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Text(
               text = stringResource(R.string.text_for_translate_is_empty),
               style = MaterialTheme.typography.titleLarge
           )

           Text(
               text = stringResource(R.string.start_typing),
               style = MaterialTheme.typography.titleMedium
           )
       }
    }
}