package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.texttospeechmanager.TTSManager
import dagger.Module
import dagger.Provides

@Module
class TTSManagerModule {

    @Provides
    @AppScope
    fun provideTTSManager(context: Context,logger: Logger) : TTSManager {
        return TTSManager.create(
            context,
            onInitSuccessful = {
                logger.info("TTSManager init is successful", LOG_TAG_FOR_TTSMANAGER)
            },
            onInitFailed = {
                logger.error("TTSManager init is failed", LOG_TAG_FOR_TTSMANAGER)
            }
        )
    }

    companion object {
        private const val LOG_TAG_FOR_TTSMANAGER = "TTSMANAGER"
    }
}