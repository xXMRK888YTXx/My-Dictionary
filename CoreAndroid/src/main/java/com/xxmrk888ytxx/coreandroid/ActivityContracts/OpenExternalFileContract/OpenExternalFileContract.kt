package com.xxmrk888ytxx.coreandroid.ActivityContracts.OpenExternalFileContract

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract


/**
 * [Ru]
 *  Activity конракт для открытия существуещего файла из внешней памяти устройтсва
 *
 *  Принимает входные параметры в виде класса [OpenExternalFileContractParams]
 *
 *  Если пользователь успешно выбрал файл то вернётся [Uri] этого файла
 *
 *  Если пользователь не выберет файл то вернётся null
 */

/**
 * [En]
 * Activity contract for open existing file from external storage of device
 *
 * Accepts input parameters in the form of class [OpenExternalFileContractParams]
 *
 * If the user has successfully selected a file, it will return [Uri] of this file
 *
 * If the user hasn't selected a file,it will return null
 */
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