package com.xxmrk888ytxx.mydictionary.presentation

import AdController
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.addwordscreen.EditWordViewModel
import com.xxmrk888ytxx.admobmanager.AdMobBanner
import com.xxmrk888ytxx.autobackuptotelegramscreen.AutoBackupToTelegramViewModel
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.createbackupscreen.CreateBackupViewModel
import com.xxmrk888ytxx.createwordgroupscreen.CreateWordGroupViewModel
import com.xxmrk888ytxx.featureviewscreen.FeatureViewViewModel
import com.xxmrk888ytxx.goals.extensions.appComponent
import com.xxmrk888ytxx.goals.extensions.setContentWithThemeAndAdController
import com.xxmrk888ytxx.managelanguagescreen.ManageLanguageViewModel
import com.xxmrk888ytxx.managetranslatedmodelsscreen.ManageModelsForTranslateViewModel
import com.xxmrk888ytxx.mydictionary.R
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

        appComponent.inject(this)

        activityViewModel.initAd()
        activityViewModel.initTTS()

        activityViewModel.loadConsentForm(this)

        setContentWithThemeAndAdController(
            adController = adController
        ) {
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

                    mainScreen(wordGroupViewModel, trainingActionViewModel, settingsViewModel,translatorViewModel)

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

    private val adController: AdController by lazy {
        object : AdController {
            @Composable
            override fun MainScreenBanner() {
                val isAdsEnabled by activityViewModel.isAdsEnabledFlow.collectAsStateWithLifecycle(
                    initialValue = true
                )

                if(isAdsEnabled) {
                    AdMobBanner(
                        adMobKey = stringResource(R.string.MainScreenBannerKey),
                        background = MaterialTheme.colorScheme.background
                    )
                }
            }

            @Composable
            override fun WordGroupScreenBanner() {
                val isAdsEnabled by activityViewModel.isAdsEnabledFlow.collectAsStateWithLifecycle(
                    initialValue = true
                )

                if(isAdsEnabled) {
                    AdMobBanner(
                        adMobKey = stringResource(R.string.WordGroupScreenBannerKey),
                        background = MaterialTheme.colorScheme.background
                    )
                }
            }

            @Composable
            override fun TrainingBanner() {
                val isAdsEnabled by activityViewModel.isAdsEnabledFlow.collectAsStateWithLifecycle(
                    initialValue = true
                )

                if(isAdsEnabled) {
                    AdMobBanner(
                        adMobKey = stringResource(R.string.TrainingBannerKey),
                        background = MaterialTheme.colorScheme.background
                    )
                }
            }

            override fun showMainScreenToTrainingScreenBanner() {
                activityViewModel.showInterstitialAd(
                    key = getString(R.string.showMainScreenToTrainingScreenBannerKey),
                    activity = this@MainActivity
                )
            }

            override fun showWordGroupScreenToViewWordOfWordGroup() {
                activityViewModel.showInterstitialAd(
                    key = getString(R.string.showWordGroupScreenToViewWordOfWordGroupKey),
                    activity = this@MainActivity
                )
            }

            override fun showWordGroupScreenToCreateWordGroupScreen() {
                activityViewModel.showInterstitialAd(
                    key = getString(R.string.WordGroupScreenToCreateWordGroupScreenKey),
                    activity = this@MainActivity
                )
            }

        }
    }


    companion object {
        private const val LOG_TAG = "MainActivity"
    }
}