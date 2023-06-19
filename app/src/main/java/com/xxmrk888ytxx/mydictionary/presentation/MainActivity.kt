package com.xxmrk888ytxx.mydictionary.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.goals.extensions.appComponent
import com.xxmrk888ytxx.goals.extensions.composeViewModel
import com.xxmrk888ytxx.goals.extensions.setContentWithTheme
import com.xxmrk888ytxx.wordgroupscreen.WordGroupScreen
import com.xxmrk888ytxx.wordgroupscreen.WordGroupViewModel
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var wordGroupViewModel: Provider<WordGroupViewModel>

    @Inject
    lateinit var activityViewModelFactory: ActivityViewModel.Factory

    private val activityViewModel by viewModels<ActivityViewModel> { activityViewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentWithTheme {
            val navController = rememberNavController()

            LaunchedEffect(key1 = navController, block = {
                activityViewModel.navController = navController
            })

            NavigationHost(
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
            }
        }
    }
}