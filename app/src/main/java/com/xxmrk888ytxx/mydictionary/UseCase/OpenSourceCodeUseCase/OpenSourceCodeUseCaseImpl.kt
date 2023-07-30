package com.xxmrk888ytxx.mydictionary.UseCase.OpenSourceCodeUseCase

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import com.xxmrk888ytxx.mydictionary.R
import javax.inject.Inject

class OpenSourceCodeUseCaseImpl @Inject constructor(
    private val context: Context,
    private val logger:Logger
) : OpenSourceCodeUseCase {

    override fun execute() {
        try {
            val url = context.getString(R.string.Source_code_url)

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(browserIntent)
        }catch (e:Exception) {
            logger.error(e, LOG_TAG)
        }
    }

    companion object {
        private const val LOG_TAG = "OpenSourceCodeUseCaseImpl"
    }
}