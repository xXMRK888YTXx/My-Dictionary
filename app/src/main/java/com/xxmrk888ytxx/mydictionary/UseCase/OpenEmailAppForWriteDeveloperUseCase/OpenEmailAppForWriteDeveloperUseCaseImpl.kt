package com.xxmrk888ytxx.mydictionary.UseCase.OpenEmailAppForWriteDeveloperUseCase

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.R
import javax.inject.Inject


class OpenEmailAppForWriteDeveloperUseCaseImpl @Inject constructor(
    private val context: Context,
    private val logger:Logger
) : OpenEmailAppForWriteDeveloperUseCase {
    
    override fun execute() {
        try {
            val email = context.getString(R.string.email)
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            val chooser = Intent.createChooser(emailIntent, context.getString(R.string.choose_a_client)).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(chooser)
        }catch (e:Exception) {
            logger.error(e,LOG_TAG)
        }
    }

    companion object {
        private const val LOG_TAG = "OpenEmailAppForWriteDeveloperUseCaseImpl"
    }
}