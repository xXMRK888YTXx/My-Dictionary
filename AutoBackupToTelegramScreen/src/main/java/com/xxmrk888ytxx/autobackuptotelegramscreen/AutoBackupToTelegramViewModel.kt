package com.xxmrk888ytxx.autobackuptotelegramscreen

import androidx.compose.material3.SnackbarHostState
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.exception.ConvertOneLineDataException
import com.xxmrk888ytxx.autobackuptotelegramscreen.exception.NoInternetException
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.LocalUiEvent
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.ScreenState
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.ScreenType
import com.xxmrk888ytxx.autobackuptotelegramscreen.models.TelegramData
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.coreandroid.getWithCast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AutoBackupToTelegramViewModel @Inject constructor(
    private val checkTelegramDataContract: CheckTelegramDataContract
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {

            is LocalUiEvent.BotKeyTextChangedEvent -> {
                botKeyState.update { event.value }

                validateNoOneLineData()
            }


            is LocalUiEvent.SaveTelegramDataEvent -> {
                val telegramData = TelegramData(userIdState.value,botKeyState.value)

                screenTypeState.update { ScreenType.LOADING }

                startSaveTelegramDataJob(telegramData,event.snackbarHostState)
            }

            is LocalUiEvent.UserIdTextChangedEvent -> {
                if(!event.value.isDigitsOnly()) return

                userIdState.update { event.value }

                validateNoOneLineData()
            }

            LocalUiEvent.WhereToGetEvent -> {

            }

            is LocalUiEvent.OnBackEvent -> {
                event.navigator.backScreen()
            }
        }
    }

    private fun validateNoOneLineData() {
        val userIdText = userIdState.value
        val botKeyText = botKeyState.value

        isSaveTelegramDataAvailableState.update {
            userIdText.isNotEmpty() && botKeyText.isNotEmpty()
        }
    }

    private suspend fun checkTelegramData(
        telegramData: TelegramData,
        snackbarHostState: SnackbarHostState
    ) : Boolean {
        return try {
            val result = checkTelegramDataContract.checkData(telegramData)

            if(!result) {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(
                        "Entered telegram data is not valid"
                    )
                }
            }

            return result
        }
        catch (e:NoInternetException) {
            viewModelScope.launch {
                snackbarHostState.showSnackbar(
                    "No internet connection."
                )
            }
            return false
        }
        catch (e:Exception) {
            viewModelScope.launch {
                snackbarHostState.showSnackbar(
                    "An unknown error has occurred"
                )
            }
            return false
        }
    }

    private fun startSaveTelegramDataJob(
        telegramData: TelegramData,
        snackbarHostState: SnackbarHostState
    ) : Job {
        return viewModelScope.launch(Dispatchers.IO) {
            val isDataValid = checkTelegramData(telegramData,snackbarHostState)

            if(!isDataValid) {
                screenTypeState.update { ScreenType.INPUT_TELEGRAM_DATA }
                return@launch
            }

            screenTypeState.update { ScreenType.BACKUP_SETTINGS }
        }
    }


    private val screenTypeState = MutableStateFlow(ScreenType.LOADING)

    private val userIdState = MutableStateFlow("")

    private val botKeyState = MutableStateFlow("")

    private val isSaveTelegramDataAvailableState = MutableStateFlow(false)


    override val state: Flow<ScreenState> = combine(
        screenTypeState,
        userIdState,
        botKeyState,
        isSaveTelegramDataAvailableState
    ) { flowArray:Array<Any> ->
        ScreenState(
            screenType = flowArray.getWithCast(0),
            userIdText = flowArray.getWithCast(1),
            botKeyText = flowArray.getWithCast(2),
            isSaveTelegramDataAvailable = flowArray.getWithCast(3),
        ).also { cashedScreenState = it }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState


    init {
        screenTypeState.update { ScreenType.INPUT_TELEGRAM_DATA }
    }
}