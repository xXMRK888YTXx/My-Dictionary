package com.xxmrk888ytxx.wordtranslatetrainingscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiEvent
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.MVI.UiModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.GenerateQuestionForTrainingContract
import com.xxmrk888ytxx.wordtranslatetrainingscreen.contracts.ProvideWordGroupsContract
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.LocalUiEvent
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.Question
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenState
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.ScreenType
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingParams
import com.xxmrk888ytxx.wordtranslatetrainingscreen.models.TrainingProgress
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordTranslateTrainingViewModel @Inject constructor(
    private val provideWordGroupsContract: ProvideWordGroupsContract,
    private val generateQuestionForTrainingContract: GenerateQuestionForTrainingContract,
) : ViewModel(), UiModel<ScreenState> {

    @OptIn(ExperimentalFoundationApi::class)
    override fun handleEvent(event: UiEvent) {
        if (event !is LocalUiEvent) return

        when (event) {
            is LocalUiEvent.NumberOfQuestionsChangedEvent -> {
                event.newValue.validateNumberOfQuestionsInput()?.let { newQuestionCount ->
                    trainingParamsState.update { it.copy(questionCount = newQuestionCount) }
                }
            }

            is LocalUiEvent.ChangeIsUsePhrasesEvent -> {
                trainingParamsState.update { it.copy(isUsePhrases = event.newValue) }
            }

            is LocalUiEvent.ChangeWordGroupSelectedStateEvent -> {
                trainingParamsState.update {
                    it.copy(
                        selectedWordGroupsId = it.selectedWordGroupsId.insertOrRemove(event.id)
                    )
                }
            }

            is LocalUiEvent.BackScreenEvent -> {
                event.navigator.backScreen()
            }

            LocalUiEvent.StartTrainingEvent -> {
                if (screenTypeState.value != ScreenType.CONFIGURATION) return

                viewModelScope.launch {
                    screenTypeState.update { ScreenType.LOADING }

                    questionListState.update {
                        generateQuestionForTrainingContract.generateQuestion(
                            viewModelScope,
                            trainingParamsState.value
                        )
                    }

                    screenTypeState.update { ScreenType.TRAINING }
                }
            }

            is LocalUiEvent.ChangeAnswerTextEvent -> {
                trainingProgressState.update {
                    it.copy(currentAnswer = event.text)
                }
            }

            is LocalUiEvent.NextQuestion -> {
                val trainingProgress = trainingProgressState.value

                if(trainingProgress.currentAnswer.isEmpty()) return

                val questionList = questionListState.value

                val isAnswerCurrent = trainingProgress.currentAnswer ==
                        questionList[trainingProgress.currentPage].translate

                viewModelScope.launch(Dispatchers.Main) {
                    trainingProgressState.update {
                        it.copy(
                            currentAnswer = ""
                        )
                    }

                    if(questionList.lastIndex != trainingProgress.currentPage) {

                        event.scope.launch {
                            event.pager.animateScrollToPage(event.pager.currentPage + 1)
                        }.join()

                        trainingProgressState.update {
                            it.copy(
                                currentPage = event.pager.currentPage,
                                correctAnswers = it.correctAnswers + if(isAnswerCurrent) 1 else 0,
                                incorrectAnswers = it.incorrectAnswers + if(!isAnswerCurrent) 1 else 0,
                            )
                        }
                    } else {
                        trainingProgressState.update {
                            it.copy(
                                correctAnswers = it.correctAnswers + if(isAnswerCurrent) 1 else 0,
                                incorrectAnswers = it.incorrectAnswers + if(!isAnswerCurrent) 1 else 0,
                            )
                        }

                        screenTypeState.update { ScreenType.RESULTS }
                    }
                }

            }
        }
    }

    private val trainingParamsState: MutableStateFlow<TrainingParams> = MutableStateFlow(
        TrainingParams()
    )

    private val screenTypeState: MutableStateFlow<ScreenType> =
        MutableStateFlow(ScreenType.CONFIGURATION)

    private val trainingProgressState: MutableStateFlow<TrainingProgress> =
        MutableStateFlow(TrainingProgress())

    private val questionListState: MutableStateFlow<ImmutableList<Question>> =
        MutableStateFlow(persistentListOf())

    override val state: Flow<ScreenState> = combine(
        trainingParamsState,
        screenTypeState,
        provideWordGroupsContract.wordGroups,
        trainingProgressState,
        questionListState
    ) { trainingParams, screenType, wordGroups, trainingStats, questionList ->

        ScreenState(
            trainingParams,
            screenType,
            wordGroups,
            trainingStats,
            questionList
        ).also { cashedScreenState = it }
    }

    private var cashedScreenState = ScreenState()

    override val defValue: ScreenState
        get() = cashedScreenState


    private fun String.validateNumberOfQuestionsInput(): Int? {
        return try {
            if (this.isEmpty()) return 1

            check(this.isDigitsOnly())

            val digit = this.toInt()

            check(digit in 1..99)

            digit
        } catch (e: Exception) {
            null
        }
    }

    private fun Set<Int>.insertOrRemove(id: Int): ImmutableSet<Int> {
        val isHaveIdInSet = contains(id)
        val newSet = mutableSetOf<Int>()

        forEach {
            newSet.add(it)
        }


        if (isHaveIdInSet) {
            newSet.remove(id)
        } else {
            newSet.add(id)
        }

        return newSet.toImmutableSet()
    }
}