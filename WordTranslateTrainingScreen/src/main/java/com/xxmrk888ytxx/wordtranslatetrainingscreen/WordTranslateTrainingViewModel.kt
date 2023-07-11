package com.xxmrk888ytxx.wordtranslatetrainingscreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.ProvideWordGroupsContract
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenType
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class WordTranslateTrainingViewModel @Inject constructor(
    private val provideWordGroupsContract: ProvideWordGroupsContract
) : ViewModel(),UiModel<ScreenState> {

    override fun handleEvent(event: UiEvent) {
        if(event !is LocalUiEvent) return

        when(event) {
            is LocalUiEvent.NumberOfQuestionsChangedEvent -> {
                event.newValue.validateNumberOfQuestionsInput()?.let { newQuestionCount ->
                    trainingParamsState.update { it.copy(questionCount = newQuestionCount) }
                }
            }

            is LocalUiEvent.ChangeIsUsePhrasesEvent -> {
                trainingParamsState.update { it.copy(isUsePhrases = event.newValue) }
            }
        }
    }

    private val trainingParamsState:MutableStateFlow<TrainingParams> = MutableStateFlow(
        TrainingParams()
    )

    private val screenTypeState:MutableStateFlow<ScreenType> = MutableStateFlow(ScreenType.CONFIGURATION)

    override val state: Flow<ScreenState> = combine(
        trainingParamsState,
        screenTypeState,
        provideWordGroupsContract.wordGroups
    ) { trainingParams,screenType,wordGroups ->
        ScreenState(
            trainingParams,
            screenType,
            wordGroups
        ).also { cashedScreenState = it }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState

    private fun String.validateNumberOfQuestionsInput() : Int? {
        return try {
            if(this.isEmpty()) return 1

            check(this.isDigitsOnly())

            val digit = this.toInt()

            check(digit in 1..99)

            digit
        }catch (e:Exception) {
            null
        }
    }
}