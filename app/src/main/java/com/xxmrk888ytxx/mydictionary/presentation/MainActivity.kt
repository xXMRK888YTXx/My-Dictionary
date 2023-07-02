package com.xxmrk888ytxx.mydictionary.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.xxmrk888ytxx.addwordscreen.EditWordScreen
import com.xxmrk888ytxx.addwordscreen.EditWordViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.corecompose.theme.ui.theme.LocalNavigator
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupScreen
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupViewModel
import com.xxmrk888ytxx.goals.extensions.appComponent
import com.xxmrk888ytxx.goals.extensions.composeViewModel
import com.xxmrk888ytxx.goals.extensions.setContentWithTheme
import com.xxmrk888ytxx.viewgroupwordsscreen.ViewGroupWordsScreen
import com.xxmrk888ytxx.viewgroupwordsscreen.ViewGroupWordsViewModel
import com.xxmrk888ytxx.wordgroupscreen.WordGroupScreen
import com.xxmrk888ytxx.wordgroupscreen.WordGroupViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        activityViewModel.initTTS()

        setContentWithTheme {
            val navController = rememberNavController()

            LaunchedEffect(key1 = navController, block = {
                activityViewModel.navController = navController
            })

            SnackBarScaffold { paddings ->
                NavigationHost(
                    paddingValues = paddings,
                    navController = navController,
                    startDestination = Screen.WordGroupScreen.route,
                    navigator = activityViewModel
                ) {

                    composable(Screen.WordGroupScreen.route) {
                        val viewModel = composeViewModel() {
                            wordGroupViewModel.get()
                        }

                        val screenState by viewModel.state.collectAsStateWithLifecycle(viewModel.defValue)

                        WordGroupScreen(
                            screenState = screenState,
                            onEvent = viewModel::handleEvent
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
                            editWordViewModel.create(wordGroupId,editWordId)
                        }

                        val screenState by viewModel.state.collectAsStateWithLifecycle(
                            initialValue = viewModel.defValue
                        )

                        EditWordScreen(screenState = screenState, onEvent = viewModel::handleEvent)
                    }
                }
            }
        }
    }

    companion object {
        private const val LOG_TAG = "MainActivity"
    }
}