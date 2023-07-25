package com.xxmrk888ytxx.coreandroid.ActivityContracts.OpenExternalFileContract

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class OpenExternalFileContract : ActivityResultContract<OpenExternalFileContractParams,Uri?>() {
    override fun createIntent(context: Context, input: OpenExternalFileContractParams): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = input.fileType
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent?.data
    }
}