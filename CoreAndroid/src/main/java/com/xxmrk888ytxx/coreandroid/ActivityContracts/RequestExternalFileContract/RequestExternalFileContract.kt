package com.xxmrk888ytxx.coreandroid.ActivityContracts.RequestExternalFileContract

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class RequestExternalFileContract : ActivityResultContract<RequestExternalFileContractParams, Uri?>() {

    override fun createIntent(context: Context, input: RequestExternalFileContractParams): Intent {
        return Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = input.fileType
            putExtra(Intent.EXTRA_TITLE, input.startFileName)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent?.data
    }
}