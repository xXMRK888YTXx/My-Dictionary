package com.xxmrk888ytxx.mydictionary.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.addwordscreen.EditWordViewModel
import com.xxmrk888ytxx.autobackuptotelegramscreen.AutoBackupToTelegramViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.createbackupscreen.CreateBackupViewModel
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupViewModel
import com.xxmrk888ytxx.featureviewscreen.FeatureViewViewModel
import com.xxmrk888ytxx.goals.extensions.appComponent
import com.xxmrk888ytxx.goals.extensions.setContentWithTheme
import com.xxmrk888ytxx.managelanguagescreen.ManageLanguageViewModel
import com.xxmrk888ytxx.managetranslatedmodelsscreen.ManageModelsForTranslateViewModel
import com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder.FirstStartAppStateHolder
import com.xxmrk888ytxx.restorebackupscreen.RestoreBackupViewModel
import com.xxmrk888ytxx.settingsscreen.SettingsViewModel
import com.xxmrk888ytxx.trainingactionsscreen.TrainingActionViewModel
import com.xxmrk888ytxx.translatorscreen.TranslatorViewModel
import com.xxmrk888ytxx.viewgroupwordsscreen.ViewGroupWordsViewModel
import com.xxmrk888ytxx.wordbyeartrainingscreen.WordByEarTrainingViewModel
import com.xxmrk888ytxx.wordgroupscreen.WordGroupViewModel
import com.xxmrk888ytxx.wordtranslatetrainingscreen.WordTranslateTrainingViewModel
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
    lateinit var wordTranslateTrainingViewModel: Provider<WordTranslateTrainingViewModel>

    @Inject
    lateinit var settingsViewModel: Provider<SettingsViewModel>

    @Inject
    lateinit var wordByEarTrainingViewModel: Provider<WordByEarTrainingViewModel>

    @Inject
    lateinit var createBackupScreenModel: Provider<CreateBackupViewModel>

    @Inject
    lateinit var restoreBackupViewModel: Provider<RestoreBackupViewModel>

    @Inject
    lateinit var manageLanguageViewModel: Provider<ManageLanguageViewModel>

    @Inject
    lateinit var featureViewViewModel: Provider<FeatureViewViewModel>

    @Inject
    lateinit var firstStartAppStateHolder: FirstStartAppStateHolder

    @Inject
    lateinit var autoBackupToTelegramViewModel: Provider<AutoBackupToTelegramViewModel>

    @Inject
    lateinit var translatorViewModel: Provider<TranslatorViewModel>

    @Inject
    lateinit var manageModelsForTranslateViewModel: Provider<ManageModelsForTranslateViewModel>


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appComponent.inject(this)

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
                    featureViewScreen(featureViewViewModel)

                    mainScreen(
                        wordGroupViewModel,
                        trainingActionViewModel,
                        settingsViewModel,
                        translatorViewModel
                    )

                    createWordGroupScreen(createWordGroupViewModel)

                    viewGroupWordsScreen(logger, viewGroupWordsViewModel)

                    editWordScreen(logger, editWordViewModel)

                    wordTranslateTrainingScreen(wordTranslateTrainingViewModel)

                    wordByEarTrainingScreen(wordByEarTrainingViewModel)

                    createBackupScreen(createBackupScreenModel)

                    restoreBackupScreen(restoreBackupViewModel)

                    manageLanguageScreen(manageLanguageViewModel)

                    manageModelsForTranslateScreen(manageModelsForTranslateViewModel)

                    autoBackupToTelegramScreen(autoBackupToTelegramViewModel)
                }
            }
        }
    }

    private fun getStartDestination(isFirstStart: Boolean): String {
        return if (isFirstStart) Screen.FeatureViewScreen.route else Screen.MainScreen.route
    }


    companion object {
        private const val LOG_TAG = "MainActivity"
    }
}