package com.xxmrk888ytxx.autobackuptotelegramscreen

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CheckTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.CreateBackupContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ManageBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.OpenWhereToGetInstructionsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ProvideBackupSettingsContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.RemoveTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.ValidateTelegramDataContract
import com.xxmrk888ytxx.autobackuptotelegramscreen.contract.SaveTelegramDataContract
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AutoBackupToTelegramViewModel @Inject constructor(
    private val validateTelegramDataContract: ValidateTelegramDataContract,
    private val saveTelegramDataContract: SaveTelegramDataContract,
    private val checkTelegramDataContract: CheckTelegramDataContract,
    private val manageBackupSettingsContract: ManageBackupSettingsContract,
    private val provideBackupSettingsContract: ProvideBackupSettingsContract,
    private val openWhereToGetInstructionsContract: OpenWhereToGetInstructionsContract,
    private val createBackupContract: CreateBackupContract,
    private val removeTelegramDataContract: RemoveTelegramDataContract,
    private val context: Context
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {

            is LocalUiEvent.BotKeyTextChangedEvent -> {
                botKeyState.update { event.value }

                validateData()
            }


            is LocalUiEvent.SaveTelegramDataEvent -> {
                val telegramData = TelegramData(userIdState.value,botKeyState.value)

                screenTypeState.update { ScreenType.LOADING }

                startSaveTelegramDataJob(telegramData,event.snackbarHostState)
            }

            is LocalUiEvent.UserIdTextChangedEvent -> {
                if(!event.value.isDigitsOnly()) return

                userIdState.update { event.value }

                validateData()
            }

            LocalUiEvent.WhereToGetEvent -> {
                openWhereToGetInstructionsContract.openInstructions()
            }

            is LocalUiEvent.OnBackEvent -> {
                event.navigator.backScreen()
            }

            is LocalUiEvent.BackupStateChanged -> {

                viewModelScope.launch(Dispatchers.IO) {
                    manageBackupSettingsContract.setBackupState(event.value)
                }
            }

            is LocalUiEvent.BackupTimeChangedEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    manageBackupSettingsContract.setBackupTime(event.backupTime)
                }
            }

            LocalUiEvent.CreateBackup -> {
                viewModelScope.launch(Dispatchers.IO) {
                    createBackupContract.createBackup()
                }
            }

            is LocalUiEvent.IsNotExecuteIfNotChangesStateChangedEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    manageBackupSettingsContract.setNotExecuteIfNotChangesState(event.value)
                }
            }

            LocalUiEvent.RemoveTelegramData -> {

                viewModelScope.launch(Dispatchers.IO) {

                    screenTypeState.update { ScreenType.LOADING }

                    removeTelegramDataContract.remove()

                    manageBackupSettingsContract.reset()

                    screenTypeState.update { ScreenType.INPUT_TELEGRAM_DATA }
                }
            }
        }
    }

    private fun validateData() {
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
            val result = validateTelegramDataContract.validateData(telegramData)

            if(!result) {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(
                        context.getString(R.string.entered_telegram_data_is_not_valid)
                    )
                }
            }

            return result
        }
        catch (e:NoInternetException) {
            viewModelScope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(R.string.no_internet_connection)
                )
            }
            return false
        }
        catch (e:Exception) {
            viewModelScope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(R.string.an_unknown_error_has_occurred)
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

            saveTelegramDataContract.saveTelegramData(telegramData)

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
        isSaveTelegramDataAvailableState,
        provideBackupSettingsContract.backupSettings
    ) { flowArray:Array<Any> ->
        ScreenState(
            screenType = flowArray.getWithCast(0),
            userIdText = flowArray.getWithCast(1),
            botKeyText = flowArray.getWithCast(2),
            isSaveTelegramDataAvailable = flowArray.getWithCast(3),
            backupSettings = flowArray.getWithCast(4)
        ).also { cashedScreenState = it }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState


    init {
        viewModelScope.launch(Dispatchers.IO) {
            if(checkTelegramDataContract.isTelegramDataExist()) {
                screenTypeState.update { ScreenType.BACKUP_SETTINGS }
            } else {
                screenTypeState.update { ScreenType.INPUT_TELEGRAM_DATA }
            }
        }
    }
}