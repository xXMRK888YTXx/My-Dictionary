package com.xxmrk888ytxx.mydictionary.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xxmrk888ytxx.addwordscreen.EditWordScreen
import com.xxmrk888ytxx.addwordscreen.EditWordViewModel
import com.xxmrk888ytxx.bottombarscreen.BottomBarScreen
import com.xxmrk888ytxx.bottombarscreen.models.BottomBarScreenModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalAdController
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.createbackupscreen.CreateBackupScreen
import com.xxmrk888ytxx.createbackupscreen.CreateBackupViewModel
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupScreen
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupViewModel
import com.xxmrk888ytxx.featureviewscreen.FeatureViewViewModel
import com.xxmrk888ytxx.goals.extensions.composeViewModel
import com.xxmrk888ytxx.managelanguagescreen.ManageLanguageScreen
import com.xxmrk888ytxx.managelanguagescreen.ManageLanguageViewModel
import com.xxmrk888ytxx.mydictionary.R
import com.xxmrk888ytxx.restorebackupscreen.RestoreBackupScreen
import com.xxmrk888ytxx.restorebackupscreen.RestoreBackupViewModel
import com.xxmrk888ytxx.settingsscreen.SettingsScreen
import com.xxmrk888ytxx.settingsscreen.SettingsViewModel
import com.xxmrk888ytxx.trainingactionsscreen.TrainingActionViewModel
import com.xxmrk888ytxx.trainingactionsscreen.TrainingActionsScreen
import com.xxmrk888ytxx.viewgroupwordsscreen.ViewGroupWordsScreen
import com.xxmrk888ytxx.viewgroupwordsscreen.ViewGroupWordsViewModel
import com.xxmrk888ytxx.wordbyeartrainingscreen.WordByEarTrainingScreen
import com.xxmrk888ytxx.wordbyeartrainingscreen.WordByEarTrainingViewModel
import com.xxmrk888ytxx.wordgroupscreen.WordGroupScreen
import com.xxmrk888ytxx.wordgroupscreen.WordGroupViewModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.WordTranslateTrainingScreen
import com.xxmrk888ytxx.wordtranslatetrainingscreen.WordTranslateTrainingViewModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Provider

fun NavGraphBuilder.featureViewScreen(
    featureViewViewModel: Provider<FeatureViewViewModel>,
) {
    composable(Screen.FeatureViewScreen.route) {
        val viewModel = composeViewModel {
            featureViewViewModel.get()
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        com.xxmrk888ytxx.featureviewscreen.FeatureViewScreen(
            screenState = screenState,
            onEvent = viewModel::handleEvent
        )
    }
}

@SuppressLint("ResourceType")
fun NavGraphBuilder.mainScreen(
    wordGroupViewModel: Provider<WordGroupViewModel>,
    trainingActionViewModel: Provider<TrainingActionViewModel>,
    settingsViewModel: Provider<SettingsViewModel>,
) {
    composable(Screen.MainScreen.route) {
        val adController = LocalAdController.current

        //WordGroupScreen
        val viewModelForWordGroupScreen = composeViewModel() {
            wordGroupViewModel.get()
        }

        val screenStateForWordGroupScreen by viewModelForWordGroupScreen.state.collectAsStateWithLifecycle(
            viewModelForWordGroupScreen.defValue
        )
        //

        //TrainingActionsScreen
        val viewModelForTrainingActionsScreen = composeViewModel {
            trainingActionViewModel.get()
        }
        //

        //SettingsScreen
        val viewModelForSettingScreen = composeViewModel() {
            settingsViewModel.get()
        }

        val screenStateForSettingsScreen by viewModelForSettingScreen.state.collectAsStateWithLifecycle(
            initialValue = viewModelForSettingScreen.defValue
        )
        //

        BottomBarScreen(
            bottomBarScreens = persistentListOf(
                BottomBarScreenModel(
                    title = stringResource(R.string.words),
                    icon = R.drawable.baseline_translate_24,
                    content = {
                        WordGroupScreen(
                            screenState = screenStateForWordGroupScreen,
                            onEvent = viewModelForWordGroupScreen::handleEvent
                        )
                    }
                ),

                BottomBarScreenModel(
                    title = stringResource(R.string.training),
                    icon = R.drawable.baseline_psychology_24,
                    content = {
                        TrainingActionsScreen(
                            onEvent = viewModelForTrainingActionsScreen::handleEvent
                        )
                    }
                ),

                BottomBarScreenModel(
                    title = stringResource(R.string.settings),
                    icon = R.drawable.baseline_settings_24,
                    content = {
                        SettingsScreen(
                            screenState = screenStateForSettingsScreen,
                            onEvent = viewModelForSettingScreen::handleEvent
                        )
                    }
                )
            ),
            bannerAd = { adController.MainScreenBanner() }
        )
    }
}

fun NavGraphBuilder.createWordGroupScreen(
    createWordGroupViewModel: Provider<CreateWordGroupViewModel>,
) {
    composable(Screen.CreateWordGroupScreen.route) {
        val viewModel = composeViewModel {
            createWordGroupViewModel.get()
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(initialValue = viewModel.defValue)

        CreateWordGroupScreen(
            screenState = screenState,
            onEvent = viewModel::handleEvent
        )
    }
}

fun NavGraphBuilder.viewGroupWordsScreen(
    logger: Logger,
    viewGroupWordsViewModel: ViewGroupWordsViewModel.Factory,
) {
    composable(
        route = "${Screen.ViewGroupWordsScreen.route}/{${NavigationKeys.WordGroupKeyForViewGroupWordsScreen.key}}",
        arguments = listOf(
            navArgument(NavigationKeys.WordGroupKeyForViewGroupWordsScreen.key) {
                type = NavType.IntType
                defaultValue = Int.MIN_VALUE
            }
        )
    ) {
        val wordGroupId =
            it.arguments?.getInt(
                NavigationKeys.WordGroupKeyForViewGroupWordsScreen.key,
                Int.MIN_VALUE
            )
                ?: return@composable

        val navigator = LocalNavigator.current


        LaunchedEffect(key1 = wordGroupId, block = {
            if (wordGroupId == Int.MIN_VALUE) {
                logger.debug("wordGroupId not setup for ${Screen.ViewGroupWordsScreen.route}")

                navigator.backScreen()
            }
        })

        val viewModel = composeViewModel {
            viewGroupWordsViewModel.create(wordGroupId)
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        ViewGroupWordsScreen(
            screenState, viewModel::handleEvent
        )
    }
}

fun NavGraphBuilder.editWordScreen(
    logger: Logger,
    editWordViewModel: EditWordViewModel.Factory,
) {
    composable(
        route = "${Screen.EditWordScreen.route}/{${NavigationKeys.WordGroupKeyForEditWordScreen.key}}/{${NavigationKeys.EditWordIdForEditWordScreen.key}}",
        arguments = listOf(
            navArgument(NavigationKeys.WordGroupKeyForEditWordScreen.key) {
                type = NavType.IntType
                defaultValue = Int.MIN_VALUE
            },
            navArgument(NavigationKeys.EditWordIdForEditWordScreen.key) {
                type = NavType.IntType
                defaultValue = 0
            }
        )
    ) {
        val wordGroupId =
            it.arguments?.getInt(
                NavigationKeys.WordGroupKeyForEditWordScreen.key,
                Int.MIN_VALUE
            )
                ?: return@composable

        val editWordId =
            it.arguments?.getInt(NavigationKeys.EditWordIdForEditWordScreen.key, 0)
                ?: 0

        val navigator = LocalNavigator.current


        LaunchedEffect(key1 = wordGroupId, block = {
            if (wordGroupId == Int.MIN_VALUE) {
                logger.debug("wordGroupId not setup for ${Screen.EditWordScreen.route}")

                navigator.backScreen()
            }
        })

        val viewModel = composeViewModel() {
            editWordViewModel.create(wordGroupId, editWordId)
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        EditWordScreen(screenState = screenState, onEvent = viewModel::handleEvent)
    }
}

fun NavGraphBuilder.wordTranslateTrainingScreen(
    wordTranslateTrainingViewModel: Provider<WordTranslateTrainingViewModel>,
) {
    composable(Screen.WordTranslateTrainingScreen.route) {
        val viewModel = composeViewModel() {
            wordTranslateTrainingViewModel.get()
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(initialValue = viewModel.defValue)

        WordTranslateTrainingScreen(
            screenState,
            viewModel::handleEvent
        )
    }
}

fun NavGraphBuilder.wordByEarTrainingScreen(
    wordByEarTrainingViewModel: Provider<WordByEarTrainingViewModel>,
) {
    composable(Screen.WordByEarTrainingScreen.route) {
        val viewModel = composeViewModel {
            wordByEarTrainingViewModel.get()
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        WordByEarTrainingScreen(
            screenState = screenState,
            onEvent = viewModel::handleEvent
        )
    }
}

fun NavGraphBuilder.createBackupScreen(
    createBackupScreenModel: Provider<CreateBackupViewModel>,
) {
    composable(Screen.CreateBackupScreen.route) {
        val viewModel = composeViewModel {
            createBackupScreenModel.get()
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        CreateBackupScreen(
            screenState = screenState,
            onEvent = viewModel::handleEvent
        )
    }
}

fun NavGraphBuilder.restoreBackupScreen(
    restoreBackupViewModel: Provider<RestoreBackupViewModel>,
) {
    composable(Screen.RestoreBackupScreen.route) {
        val viewModel = composeViewModel() {
            restoreBackupViewModel.get()
        }

        val screenState by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        RestoreBackupScreen(
            screenState = screenState,
            onEvent = viewModel::handleEvent
        )
    }
}

fun NavGraphBuilder.manageLanguageScreen(
    manageLanguageDialogState: Provider<ManageLanguageViewModel>,
) {
    composable(Screen.ManageLanguageScreen.route) {
        val viewModel = composeViewModel {
            manageLanguageDialogState.get()
        }

        val state by viewModel.state.collectAsStateWithLifecycle(
            initialValue = viewModel.defValue
        )

        ManageLanguageScreen(
            screenState = state,
            onEvent = viewModel::handleEvent
        )
    }
}

