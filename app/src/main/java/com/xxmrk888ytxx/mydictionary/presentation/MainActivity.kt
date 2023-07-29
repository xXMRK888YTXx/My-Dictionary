package com.xxmrk888ytxx.mydictionary.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.xxmrk888ytxx.addwordscreen.EditWordScreen
import com.xxmrk888ytxx.addwordscreen.EditWordViewModel
import com.xxmrk888ytxx.bottombarscreen.BottomBarScreen
import com.xxmrk888ytxx.bottombarscreen.models.BottomBarScreenModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.createbackupscreen.CreateBackupScreen
import com.xxmrk888ytxx.createbackupscreen.CreateBackupViewModel
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupScreen
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupViewModel
import com.xxmrk888ytxx.featureviewscreen.FeatureViewScreen
import com.xxmrk888ytxx.featureviewscreen.FeatureViewViewModel
import com.xxmrk888ytxx.goals.extensions.appComponent
import com.xxmrk888ytxx.goals.extensions.composeViewModel
import com.xxmrk888ytxx.goals.extensions.setContentWithTheme
import com.xxmrk888ytxx.managelanguagescreen.ManageLanguageScreen
import com.xxmrk888ytxx.managelanguagescreen.ManageLanguageViewModel
import com.xxmrk888ytxx.mydictionary.R
import com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder.FirstStartAppStateHolder
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
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var wordGroupViewModel: Provider<WordGroupViewModel>

    @Inject
    lateinit var activityViewModelFactory: ActivityViewModel.Factory

    @Inject
    lateinit var createWordGroupViewModel: Provider<CreateWordGroupViewModel>

    private val activityViewModel by viewModels<ActivityViewModel> { activityViewModelFactory }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var viewGroupWordsViewModel: ViewGroupWordsViewModel.Factory

    @Inject
    lateinit var editWordViewModel: EditWordViewModel.Factory

    @Inject
    lateinit var trainingActionViewModel: Provider<TrainingActionViewModel>

    @Inject
    lateinit var wordTranslateTrainingViewModel : Provider<WordTranslateTrainingViewModel>

    @Inject
    lateinit var settingsViewModel: Provider<SettingsViewModel>

    @Inject
    lateinit var wordByEarTrainingViewModel: Provider<WordByEarTrainingViewModel>

    @Inject
    lateinit var createBackupScreenModel: Provider<CreateBackupViewModel>

    @Inject
    lateinit var restoreBackupViewModel: Provider<RestoreBackupViewModel>

    @Inject
    lateinit var manageLanguageDialogState: Provider<ManageLanguageViewModel>

    @Inject
    lateinit var featureViewViewModel: Provider<FeatureViewViewModel>

    @Inject
    lateinit var firstStartAppStateHolder: FirstStartAppStateHolder

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        activityViewModel.initAd()
        activityViewModel.initTTS()

        setContentWithTheme {
            val navController = rememberNavController()

            val isFirstStart by firstStartAppStateHolder.isFirstAppStart.collectAsStateWithLifecycle(
                initialValue = false
            )


            LaunchedEffect(key1 = navController, block = {
                activityViewModel.navController = navController
            })

            SnackBarScaffold { paddings ->
                NavigationHost(
                    paddingValues = paddings,
                    navController = navController,
                    startDestination = getStartDestination(isFirstStart),
                    navigator = activityViewModel
                ) {
                    composable(Screen.FeatureViewScreen.route) {
                        val viewModel = composeViewModel {
                            featureViewViewModel.get()
                        }

                        val screenState by viewModel.state.collectAsStateWithLifecycle(
                            initialValue = viewModel.defValue
                        )

                        FeatureViewScreen(
                            screenState = screenState,
                            onEvent = viewModel::handleEvent
                        )
                    }


                    composable(Screen.MainScreen.route) {

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
                            )
                        )
                    }

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
            }
        }
    }

    private fun getStartDestination(isFirstStart:Boolean) : String {
        return if(isFirstStart) Screen.FeatureViewScreen.route else Screen.MainScreen.route
    }

    companion object {
        private const val LOG_TAG = "MainActivity"
    }
}