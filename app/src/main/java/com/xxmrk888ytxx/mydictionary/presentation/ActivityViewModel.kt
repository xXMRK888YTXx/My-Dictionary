package com.xxmrk888ytxx.mydictionary.presentation

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.xxmrk888ytxx.admobmanager.AdMobManager
import com.xxmrk888ytxx.admobmanager.ConsentFormLoader
import com.xxmrk888ytxx.androidcore.runOnUiThread
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Navigator
import com.xxmrk888ytxx.mydictionary.BuildConfig
import com.xxmrk888ytxx.mydictionary.domain.AdsStateManager.AdsStateManager
import com.xxmrk888ytxx.mydictionary.domain.FirstStartAppStateHolder.FirstStartAppStateHolder
import com.xxmrk888ytxx.texttospeechmanager.TTSManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class ActivityViewModel @Inject constructor(
    private val ttsManager: TTSManager,
    private val adMobManager: AdMobManager,
    private val adsStateManager: AdsStateManager
) : ViewModel(),Navigator {

    val isAdsEnabledFlow = adsStateManager.isAdsEnabledFlow

    var navController:NavController? = null

    private var isConsentChecked:Boolean = false

    private var adsShowCounter = 0

    fun initTTS() {
        ttsManager.init()
    }

    fun initAd() {
        adMobManager.initAdmob()
    }


    override fun toCreateWordGroupScreen() = runOnUiThread {
        navigate(Screen.CreateWordGroupScreen)
    }

    override fun toViewGroupWordsScreen(wordGroupId: Int) = runOnUiThread {
        navController?.navigate(
            route = "${Screen.ViewGroupWordsScreen.route}/$wordGroupId"
        ) {
            launchSingleTop = true
        }
    }

    override fun toEditWordScreen(wordGroupId: Int,editWordId:Int) = runOnUiThread {
        navController?.navigate(
            route = "${Screen.EditWordScreen.route}/$wordGroupId/$editWordId"
        ) {
            launchSingleTop = true
        }
    }

    override fun backScreen() = runOnUiThread {
        navController?.navigateUp()
    }

    override fun toWordTranslateTraining() = runOnUiThread {
        navController?.navigate(Screen.WordTranslateTrainingScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toWordByEarTraining() = runOnUiThread {
        navController?.navigate(Screen.WordByEarTrainingScreen.route) {
            launchSingleTop = true
        }
    }

    override fun toCreateBackupScreen() = runOnUiThread {
        navigate(Screen.CreateBackupScreen)
    }

    override fun toRestoreBackupScreen() {
        navigate(Screen.RestoreBackupScreen)
    }

    override fun toLanguageManageScreen() {
        navigate(Screen.ManageLanguageScreen)
    }

    override fun toMainScreen() = runOnUiThread {
        navController?.navigate(Screen.MainScreen.route) {

            popUpTo(Screen.FeatureViewScreen.route) { inclusive = true }

            launchSingleTop = true
        }
    }


    private fun navigate(screen:Screen) = runOnUiThread {
        navController?.navigate(screen.route) {
            launchSingleTop = true
        }
    }

    fun showInterstitialAd(key:String,activity: Activity) {
        if(!runBlocking { isAdsEnabledFlow.first() } || adsShowCounter >= 2) return

        adsShowCounter += 1

        if(adsShowCounter >= 2) {
            viewModelScope.launch(Dispatchers.Default) {
                delay(60_000 * 10)

                withContext(Dispatchers.Main) {
                    adsShowCounter = 0
                }
            }
        }


        adMobManager.showInterstitialAd(key, activity)
    }

    fun loadConsentForm(activity: Activity) {
        if(isConsentChecked) return

        isConsentChecked = true

        val logTag = "ConsentFormLoader"

        val loader = ConsentFormLoader.create(
            activity,
            BuildConfig.DEBUG,
            true
        )

        loader.checkFormState(
            onFormPrepared = {
                Log.i(logTag, "onFormPrepared")

                loader.loadAndShowForm(
                    onSuccessLoad = {
                        Log.i(logTag, "onSuccessLoad")
                    },
                    onLoadError = {
                        Log.e(logTag, "onLoadError")

                    },
                    onDismissed = {
                        Log.i(logTag, "onDismissed")
                    }
                )
            },
            onFormNotAvailable = {
                Log.e(logTag, "onFormNotAvailable")
            },
            onError = {
                Log.e(logTag, "onError")
            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModel:Provider<ActivityViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModel.get() as T
        }
    }
}