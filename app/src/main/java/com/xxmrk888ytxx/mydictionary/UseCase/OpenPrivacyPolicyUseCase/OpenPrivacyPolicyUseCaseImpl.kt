package com.xxmrk888ytxx.mydictionary.UseCase.OpenPrivacyPolicyUseCase

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.R
import javax.inject.Inject

class OpenPrivacyPolicyUseCaseImpl @Inject constructor(
    private val context: Context,
    private val logger: Logger
) : OpenPrivacyPolicyUseCase {

    override fun execute() {
        try {
            val url = context.getString(R.string.privacy_policy_url)

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(browserIntent)
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)
        }
    }
    
    companion object {
        private const val LOG_TAG = "OpenPrivacyPolicyUseCaseImpl"
    }
}