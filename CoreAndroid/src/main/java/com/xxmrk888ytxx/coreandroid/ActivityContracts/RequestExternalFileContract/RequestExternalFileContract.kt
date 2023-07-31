package com.xxmrk888ytxx.coreandroid.ActivityContracts.RequestExternalFileContract

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.xxmrk888ytxx.coreandroid.ActivityContracts.OpenExternalFileContract.OpenExternalFileContractParams


/**
 * [Ru]
 *  Activity конракт для создания файла во внешней памяти устройтсва
 *
 *  Принимает входные параметры в виде класса [RequestExternalFileContractParams]
 *
 *  Если пользователь успешно выбрал файл то вернётся [Uri] этого файла
 *
 *  Если пользователь не выберет файл то вернётся null
 */

/**
 * [En]
 * Activity contract for create new file in external storage of device
 *
 * Accepts input parameters in the form of class [RequestExternalFileContractParams]
 *
 * If the user has successfully selected a file, it will return [Uri] of this file
 *
 * If the user hasn't selected a file,it will return null
 */
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